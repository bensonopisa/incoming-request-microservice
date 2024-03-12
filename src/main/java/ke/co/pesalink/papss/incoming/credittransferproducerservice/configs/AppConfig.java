package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@Component
public class AppConfig {
    @Value("${adapter.inbound.queue.credit-transfer}")
    private String creditTransferQueue;

    @Value("${adapter.inbound.queue.status-query}")
    private String transactionStatusQueryQueue;

    @Value("${adapter.inbound.queue.account-validation}")
    private String accountValidationQueue;

    @Value("${adapter.inbound.routingKey.credit-transfer}")
    private String creditTransferRoutingKey;

    @Value("${adapter.inbound.routingKey.status-query}")
    private String statusQueryRoutingKey;

    @Value("${adapter.inbound.routingKey.account-validation}")
    private String accountValidationRoutingKey;

    @Value("${spring.rabbitmq.template.exchange}")
    private String adapterExchange;

    @DurationUnit(ChronoUnit.SECONDS)
    @Value("${adapter.poller.interval.seconds}")
    private Duration pollerInterval;


    @Value("${adapter.inbout.queue.persistence}")
    private String peristenceQueue;

    @Value("${adapter.inbound.routingKey.persistence}")
    private String persistenceRoutingKey;

    @Value("${papss.security.keyAlias}")
    private String keyStoreAlias;
    @Value("${papss.security.keystoreType}")
    private String keyStoreType;
    @Value("${papss.security.provider}")
    private String keyStoreProvider;
    @Value("${papss.security.keyPass}")
    private String keyStorePassword;
    @Value("${papss.ips.Dns}")
    private String papssIpsDns;
    @Value("${papss.ips.port}")
    private int papssIpsPort;
    @Value("${papss.poll.messages.path}")
    private String ipsMessagePath;

    @Value("${papss.security.keyStorePath}")
    private String keyStorePath;
}

