package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import jakarta.annotation.PostConstruct;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.AdapterTransactionRequest;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageRoutingService {
    private final RabbitTemplate rabbitTemplate;
    private final AppConfig appConfig;
    private final Logger logger  = LoggerFactory.getLogger(MessageRoutingService.class);
    /**
     * Holds a mapping of the message type to the routing key of the appropriate queue
     * This value will be used to identify the queue to be used based on the message type
     */
    private final Map<String, String> router = new HashMap<>();

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

    public void enQueue(String messageType, AdapterTransactionRequest transactionRequest) {
        String value = this.router.get(messageType);
        sendToQueue(value, transactionRequest);
    }

    private void sendToQueue(String routingKey, AdapterTransactionRequest body) {
        logger.info("Sending message to queue");

        try {
            this.rabbitTemplate.convertAndSend(appConfig.getAdapterExchange(), routingKey, body);
        }catch(AmqpException exception) {
            logger.error("Failed to send message to queue: Reason {}", exception.getLocalizedMessage());
        }
    }
}
