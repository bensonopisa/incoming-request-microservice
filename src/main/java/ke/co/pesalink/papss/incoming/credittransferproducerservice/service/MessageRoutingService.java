package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.TransactionRequest;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.ProcessingFailedException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MessageRoutingService {
    private final RabbitTemplate rabbitTemplate;
    private final AppConfig appConfig;
    private final Logger logger  = LoggerFactory.getLogger(MessageRoutingService.class);

    private final ObjectMapper objectMapper;
    /**
     * Holds a mapping of the message type to the routing key of the appropriate queue
     * This value will be used to identify the queue to be used based on the message type
     */
    private final Map<String, String> router = new ConcurrentHashMap<>();
    /**
     * Pre-initializes the queue router
     */
    @PostConstruct
    private void init() {
        router.put(Constants.pacs008, appConfig.getCreditTransferRoutingKey());
        router.put(Constants.pac028, appConfig.getStatusQueryRoutingKey());
        router.put(Constants.acmt023, appConfig.getAccountValidationRoutingKey());
        router.put(Constants.recon001, appConfig.getCreditTransferRoutingKey());
    }

    public void enQueue(String messageType, Message messageBody) {

        TransactionRequest transactionRequest = buildTransactionRequest(messageBody);
        // get the routing key if it exists and route the request to the appropriate queue

        if (router.containsKey(messageType)) {
            sendToQueue(router.get(messageType),transactionRequest);
        }
    }

    private void sendToQueue(String routingKey, TransactionRequest body) {
        logger.info("Sending message to queue");

        try {
            this.rabbitTemplate.convertAndSend(appConfig.getAdapterExchange(), routingKey, body);
        }catch(AmqpException exception) {
            logger.error("Failed to send message to queue: Reason {}", exception.getLocalizedMessage());
        }
    }

    private TransactionRequest buildTransactionRequest(Message transaction) {
        String bodyAsString;
        try {
            bodyAsString = objectMapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            throw new ProcessingFailedException("Failed to serialize the body", e);
        }
        return new TransactionRequest(bodyAsString, appConfig.getCreditTransferType());
    }
}
