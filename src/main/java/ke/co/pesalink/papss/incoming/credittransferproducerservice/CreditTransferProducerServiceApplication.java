package ke.co.pesalink.papss.incoming.credittransferproducerservice;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import montran.message.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class CreditTransferProducerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CreditTransferProducerServiceApplication.class, args);
	}
}