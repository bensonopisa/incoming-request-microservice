package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.Constants;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SharedMethods;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessagePollerService implements MessagePoller{
    private final SharedMethods sharedMethods;

    @Override
    public void run() {
        ResponseEntity<String> response = sharedMethods.makeApiRequest();

        if (response == null) {
            log.error("Failed to make api call. Trying again in 5 seconds");
            return;
        }
        process(response);
    }

    void process(ResponseEntity<String> response) {

        String message = response.getBody();

        HttpHeaders responseHeaders = response.getHeaders();

        Optional<String> messageSequenceNumber = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-MessageSeq"));
        Optional<String> messageType = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-MessageType"));
        Optional<String> possibleDuplicate = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-PossibleDuplicate"));
        Optional<String> remainingMessages = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-RemainingOutputs"));
        Optional<String> pollingStatus = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-ReqSts"));

        if (pollingStatus.isPresent() && StringUtils.equals(pollingStatus.get(), "EMPTY")) {
            log.info("No pending messages found...polling again in 5 seconds");
            return;
        }

        messageSequenceNumber.ifPresent( sequence  -> log.info("Message sequence number {}", sequence));
        possibleDuplicate.ifPresent( duplicate -> log.info("Possible duplicate {}", duplicate));
        messageType.ifPresent( type -> log.info("Message type {}", type));
        remainingMessages.ifPresent( messages -> log.info("Messages pending {}", messages));


        try {
            sharedMethods.validateSignature(message);
        } catch (MarshalException | ParserConfigurationException | IOException | KeyStoreException |
                 XMLSignatureException | SAXException e) {
            log.error(e.getMessage());
        }

        String type = messageType.orElseThrow(() -> new RuntimeException("Message type is null."));

        if (StringUtils.equals(type, Constants.pacs008)) {
            // replyToPayment with a accepted status
        }else {
            // confirm receipt of messages for papss rtp to remove it from their internal queues
        }

        // proceed with processing
    }

    protected void replyToPayment(String message, String status) {

    }
}


