package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessagePollerService implements MessagePoller{
    private final Logger logger = LoggerFactory.getLogger(MessagePollerService.class);
    private final AppConfig appConfig;

    private final RestTemplate restTemplate;

    private final MessageRoutingService messageRoutingService;

    @Override
    public void run() {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set(HttpHeaders.CONTENT_TYPE , MediaType.APPLICATION_XML.toString());
            httpHeaders.add("X-PAPSSRTP-Channel", "LR2020");
            httpHeaders.add("X-PAPSS-RTP-Version", "1");

            URI uri = UriComponentsBuilder.fromHttpUrl(appConfig.getPapssIpsDns())
                    .port(appConfig.getPapssIpsPort())
                    .path(appConfig.getIpsMessagePath())
                    .build().toUri();

            RequestEntity<?> request = RequestEntity.get(uri).headers(httpHeaders).build();

            logger.info("Sending request to {} ", uri);

            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            logger.info("Received response {}.", response);

            HttpHeaders responseHeaders = response.getHeaders();

            String messageSequenceNumber;
            String messageType = null;
            String remainingMessages;

            boolean possibleDuplicate;

            if (responseHeaders.containsKey("X-PAPSSRTP-MessageSeq")) {
                messageSequenceNumber = responseHeaders.getFirst("X-PAPSSRTP-MessageSeq");
                logger.info("Message sequence number {}", messageSequenceNumber);
            }

            if (responseHeaders.containsKey("X-PAPSSRTP-MessageType")) {
                messageType = responseHeaders.getFirst("X-PAPSSRTP-MessageType");
                logger.info("Message Type {}", messageType);
            }

            if (responseHeaders.containsKey(("X-PAPSSRTP-PossibleDuplicate"))) {
                possibleDuplicate = Boolean.parseBoolean(responseHeaders.getFirst("X-PAPSSRTP-PossibleDuplicate"));
                logger.info("Possible duplicate {}", possibleDuplicate);
            }

            if (responseHeaders.containsKey(("X-PAPSSRTP-RemainingOutputs"))) {
                remainingMessages = responseHeaders.getFirst("X-PAPSSRTP-RemainingOutputs");

                logger.info("{} messages remaining to be processed", remainingMessages);
            }


            boolean isValid = validateSignature(response.getBody());

            if (!isValid) {
                // return a pac002 with a rejected status
                return;
            }

            if(messageType != null){
                routeMessageToQueue(messageType, response.getBody());
            }

            // return acknowledgement
        }catch(ResourceAccessException resourceAccessException) {
            logger.error("Failed to poll messages from papss", resourceAccessException);
        }
    }

    private boolean validateSignature(String xml)  {
        boolean isValid = false;
        try {
            XmlUtils xmlUtils = new XmlUtils(appConfig);
            isValid = xmlUtils.validateSignature(xml);
        }catch(MarshalException | ParserConfigurationException | IOException | KeyStoreException | XMLSignatureException | SAXException ex) {
            logger.error("Error occured when validating signature ", ex);
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
