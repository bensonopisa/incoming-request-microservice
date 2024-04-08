package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import montran.message.Message;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    private final RabbitTemplate rabbitTemplate;

    private final AppConfig appConfig;

    @Override
    public void saveTransaction(Message message) {
        log.info("Sending request to the database");
        enQueue(message);
    }

    private void enQueue(Message message) {
        try {
            rabbitTemplate.convertAndSend(appConfig.getAdapterExchange(),appConfig.getPersistenceRoutingKey(), message);
        }catch(AmqpException ex) {
            log.error("Sending request to the db failed...", ex);
        }
    }
}
