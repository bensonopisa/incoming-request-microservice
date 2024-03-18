package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.acmt_023_001.IdentificationVerificationRequestV02;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_008_001.FIToFICustomerCreditTransferV07;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_028_001.FIToFIPaymentStatusRequestV02;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.AdapterTransactionRequest;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.ProcessingFailedException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.UnmarshallException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.HttpClient;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import montran.message.Message;
import montran.rcon.Recon;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.HttpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class MessagePollerService implements MessagePoller{
    private final AppConfig appConfig;

    private final HttpClient httpClient;

    private final MessageRoutingService messageRoutingService;

    private final XmlUtils xmlUtils;

    public MessagePollerService(AppConfig appConfig, HttpClient httpClient, MessageRoutingService messageRoutingService) {
        this.appConfig = appConfig;
        this.httpClient = httpClient;
        this.messageRoutingService = messageRoutingService;
        this.xmlUtils = new XmlUtils(appConfig);
    }


    @Override
    public void run() {
        URI uri = UriComponentsBuilder.fromHttpUrl(appConfig.getPapssIpsDns())
                .port(appConfig.getPapssIpsPort())
                .path(appConfig.getIpsMessagePath())
                .build().toUri();

        ResponseEntity<String> response;

        try {
            // I use an empty map since no additional  headers are required
           response = httpClient.makeHttpCall(uri, HttpMethod.GET, null, new HashMap<>());
        }catch(HttpException exception) {
            log.error(exception.getMessage());
            return;
        }


        String message = response.getBody();
        HttpHeaders responseHeaders = response.getHeaders();

        String messageSequenceNumber = null;
        String messageType =  null;
        String remainingMessages = null;

        boolean possibleDuplicate;


        if (responseHeaders.containsKey("X-PAPSSRTP-ReqSts") && StringUtils.equals(responseHeaders.getFirst("X-PAPSSRTP-ReqSts"), "EMPTY")) {
            log.info("No messages available now...polling again in 5 seconds");
            return;
        }

        if (responseHeaders.containsKey("X-PAPSSRTP-MessageSeq")) {
            messageSequenceNumber = responseHeaders.getFirst("X-PAPSSRTP-MessageSeq");
            log.info("Message sequence number: {}", messageSequenceNumber);
        }

        if (responseHeaders.containsKey("X-PAPSSRTP-MessageType")) {
            messageType = responseHeaders.getFirst("X-PAPSSRTP-MessageType");
            log.info("Message type: {}", messageType);
        }

        if (responseHeaders.containsKey(("X-PAPSSRTP-PossibleDuplicate"))) {
            possibleDuplicate = Boolean.parseBoolean(responseHeaders.getFirst("X-PAPSSRTP-PossibleDuplicate"));
            log.info("Possible duplicate: {}", possibleDuplicate);
        }

        if (responseHeaders.containsKey(("X-PAPSSRTP-RemainingOutputs"))) {
            remainingMessages = responseHeaders.getFirst("X-PAPSSRTP-RemainingOutputs");

            log.info("{} messages remaining to be processed", remainingMessages);
        }

        // send back an acknowledgement

        boolean isValid = true;

        if (!isValid) {
            // return a pac002 with a rejected status
            return;
        }
        log.info("Message {}", message);

        if (messageType != null ){
            if (messageType.equals(Constants.recon001)) {
                Recon recon  = (Recon) xmlUtils.unmarshall(message, Recon.class);

                log.info("Recon {}", recon);

                return;
            }

            routeMessageToQueue(messageType, message);
        }
    }

    private boolean validateSignature(String xml)  {
        boolean isValid = false;
        try {
            XmlUtils xmlUtils = new XmlUtils(appConfig);
            isValid = xmlUtils.validateSignature(xml);
        }catch(MarshalException | ParserConfigurationException | IOException | KeyStoreException | XMLSignatureException | SAXException ex) {
            log.error("Error occurred when validating signature ", ex);
        }
        return isValid;
    }

    /**
     * @apiNote route messages to appropriate queue based on the message type
     * @param messageType the type of message
     */
    private void routeMessageToQueue(String messageType, String messageBody) {
        Objects.requireNonNull(messageType, "message type cannot be null");

        String payload = "";
        Message message = readRequestBody(messageBody);

       try{
           switch(messageType) {
               case Constants.pacs008 ->  {
                   // this is a credit transfer request
                   FIToFICustomerCreditTransferV07 creditTransfer = message.getFIToFICstmrCdtTrf();
                   payload = xmlUtils.marshall(creditTransfer);
               }
               case Constants.acmt023 ->  {
                   // account validation
                   IdentificationVerificationRequestV02 verificationRequest = message.getIdVrfctnReq();
                   payload = xmlUtils.marshall(verificationRequest);
               }
               case Constants.pac028 -> {
                   // tsq
                   FIToFIPaymentStatusRequestV02 paymentStatusRequest = message.getFIToFIPmtStsReq();
                   payload = xmlUtils.marshall(paymentStatusRequest);
               }
               default -> {
                    log.info("Received message at {}", message.getAppHdr().getCreDt());
                    return;
               }
           }
       }catch(Exception e) {
           log.error("Error encountered while marshalling messages ", e);
       }
        AdapterTransactionRequest adapterTransactionRequest = new AdapterTransactionRequest(payload, appConfig.getCreditTransferType(), LocalDateTime.now());
        messageRoutingService.enQueue(messageType, adapterTransactionRequest);
    }

    Message readRequestBody(String body) {
        try {
            return (Message) xmlUtils.unmarshall(body, Message.class);
        }catch(UnmarshallException e) {
            throw new ProcessingFailedException("Failed to marshall request body", e);
        }
    }
}


