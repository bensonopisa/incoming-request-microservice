package ke.co.pesalink.papss.incoming.credittransferproducerservice.service.impl;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.TransactionRequest;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.service.RequestQueueHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusQueryRequestHandler implements RequestQueueHandler {

    private final AppConfig appConfig;
    private final RabbitTemplate rabbitTemplate;

    private final Logger logger = LoggerFactory.getLogger(StatusQueryRequestHandler.class);
    @Override
    public void queueRequest(TransactionRequest transactionRequest) {
        logger.info("Received status query request {} to send to {} queue", transactionRequest, appConfig.getTransactionStatusQueryQueue());
        try {
            this.rabbitTemplate.convertAndSend(appConfig.getAdapterExchange(), appConfig.getStatusQueryRoutingKey(), transactionRequest);
        }catch(AmqpException amqpException ){
            logger.error("Exception : {}", amqpException.getLocalizedMessage());
            // handle this more gracefully
        }
    }
}
