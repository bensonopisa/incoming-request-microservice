package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.ProcessingFailedException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.SignatureValidationException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.UnmarshallException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.Constants;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.HttpClient;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SharedMethods;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import montran.message.Message;
import montran.rcon.Recon;
import org.apache.hc.core5.http.HttpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessagePollerService implements MessagePoller{
    private final AppConfig appConfig;

    private final HttpClient httpClient;

    private final MessageRoutingService messageRoutingService;

    private final XmlUtils xmlUtils;

    private final SharedMethods sharedMethods;

    @Override
    public void run() {
        ResponseEntity<String> response;
        try {
            // I use an empty map since no additional  headers are required
           response = httpClient.makeHttpCall(appConfig.getIpsMessagePath(), HttpMethod.GET, null, new HashMap<>());
        }catch(HttpException exception) {
            log.error(exception.getMessage());
            return;
        }
        process(response);
    }

    void process(ResponseEntity<String> response) {
        String message = response.getBody();

        HttpHeaders responseHeaders = response.getHeaders();

        String messageSequenceNumber = null;
        String messageType =  Constants.pacs008;
        String remainingMessages = null;

        boolean possibleDuplicate;

        Optional<String> pollingRequestStatus = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-ReqSts"));

        if (pollingRequestStatus.isPresent() && pollingRequestStatus.get().equals("EMPTY")) {
            log.info("No message found..polling again in {} seconds", appConfig.getPollerInterval().getSeconds());
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
            log.warn("Possible duplicate: {}", possibleDuplicate);
        }

        if (responseHeaders.containsKey(("X-PAPSSRTP-RemainingOutputs"))) {
            remainingMessages = responseHeaders.getFirst("X-PAPSSRTP-RemainingOutputs");

            log.info("{} messages remaining to be processed", remainingMessages);
        }


//        if (messageType != null) {
//            if (messageType.equals(Constants.pacs008)) {
//                // send pac002 ack
//                replyToPayment(message);
//            }else {
//                // confirm message
//                confirmMessage(messageSequenceNumber);
//            }
//        }

        boolean signatureValid = validateSignature(message);

        log.info("Signature valid {}", signatureValid);


        if (messageType.equals(Constants.recon001) ) {
            Recon recon  = (Recon) xmlUtils.unmarshall(message, Recon.class);

            log.info("Recon {}", recon);

            confirmMessage(messageSequenceNumber);
            return;
        }

        if(messageType.equals(Constants.pacs002)) {
            confirmMessage(messageSequenceNumber);
        }

        messageRoutingService.enQueue(messageType,message);
    }

    private boolean validateSignature(String xml){
        try {
            return sharedMethods.validateSignature(xml);
        }catch(SignatureValidationException e) {
            log.error("Signature validation failed ", e);
            return false;
        }
    }

    private void confirmMessage(String sequenceNumber) {
        log.info("Sending confirmation request to papss for message sequence {}", sequenceNumber);

        Map<String, String> additionalHeaders = Map.of("X-PAPSSRTP-MessageSeq", sequenceNumber);

        try {
            httpClient.makeHttpCall(appConfig.getIpsAcknowldgementPath(), HttpMethod.POST, null, additionalHeaders);
        }catch(HttpException e) {
            log.error("Exception encountered",e);
        }
        log.info("Message successfully confirmed...");
    }

    public void replyToPayment(String body) {
        Message message = (Message) xmlUtils.unmarshall(body, Message.class);
        Map<String, String> template = buildPacs002Template(message);
    }

    private Map<String, String> buildPacs002Template(Message message){
        Map<String, String> templateData = new HashMap<>();

//        templateData.put("fr_bicfi", message.getFIToFICstmrCdtTrf().getGrpHdr().)

        return templateData;
    }
}


