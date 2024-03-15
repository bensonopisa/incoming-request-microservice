package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.HttpClient;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagePollerService implements MessagePoller{
    // @Todo replace logger with aspect oriented logging
    
    private final AppConfig appConfig;

    private final HttpClient httpClient;

    private final MessageRoutingService messageRoutingService;

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
        if(messageType != null){
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
    private void routeMessageToQueue(String messageType, String message) {
        Objects.requireNonNull(messageType, "message type cannot be null");



        messageRoutingService.enQueue(messageType, message);
    }
}
