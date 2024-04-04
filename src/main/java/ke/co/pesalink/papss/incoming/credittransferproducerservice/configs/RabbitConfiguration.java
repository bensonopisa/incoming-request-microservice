package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Define all the queues and bind them to the same exchange
 * the service will route the message to the appropriate queue based on the routing key
 */
@Configuration
public class RabbitConfiguration {

    private final AppConfig appConfig;

    public RabbitConfiguration(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
        return new RabbitAdmin(rabbitTemplate);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue creditTransferQueue() {
        return new Queue(appConfig.getCreditTransferQueue(), true, false, false);
    }

    @Bean
    public Queue statusQueryQueue() {
        return new Queue(appConfig.getTransactionStatusQueryQueue(), true, false, false);
    }

    @Bean
    public Queue persistenceQueue() {
        return new Queue(appConfig.getPeristenceQueue(), true, false, false);
    }

    @Bean
    public Queue accountValidationQueue() {
        return new Queue(appConfig.getAccountValidationQueue(), true, false, false);
    }

    @Bean
    public Exchange adapterExchange() {
        return new DirectExchange(appConfig.getAdapterExchange(),true, false);
    }

    @Bean
    public Binding creditTransferBinding() {
        return BindingBuilder.bind(creditTransferQueue())
                .to(adapterExchange())
                .with(appConfig.getCreditTransferRoutingKey()).noargs();
    }

    @Bean
    public Binding accountValidationBinding() {
        return BindingBuilder.bind(accountValidationQueue())
                .to(adapterExchange())
                .with(appConfig.getAccountValidationRoutingKey()).noargs();
    }

    @Bean
    public Binding statusQueryBinding() {
        return BindingBuilder.bind(statusQueryQueue())
                .to(adapterExchange())
                .with(appConfig.getStatusQueryRoutingKey()).noargs();
    }

    @Bean
    public Binding persistenceBinding() {
        return BindingBuilder.bind(persistenceQueue())
                .to(adapterExchange())
                .with(appConfig.getPersistenceRoutingKey()).noargs();
    }
}
