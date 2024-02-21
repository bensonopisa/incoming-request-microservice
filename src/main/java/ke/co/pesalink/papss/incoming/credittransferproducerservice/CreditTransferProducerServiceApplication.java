package ke.co.pesalink.papss.incoming.credittransferproducerservice;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class CreditTransferProducerServiceApplication{
	public static void main(String[] args) {
		SpringApplication.run(CreditTransferProducerServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RabbitTemplate rabbitTemplate, AppConfig appConfig) {
		return args -> rabbitTemplate
				.convertAndSend(appConfig.getAdapterExchange(), appConfig.getCreditTransferRoutingKey(), "testing rabbit mq");
	}
}