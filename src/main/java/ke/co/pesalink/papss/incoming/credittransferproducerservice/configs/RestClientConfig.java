package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.handlers.CustomResponseErrorHandler;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.interceptors.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Rest client configurations
 */
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
    private final AppConfig appConfig;
    private final LoggingInterceptor loggingInterceptor;
    private final CustomResponseErrorHandler customResponseErrorHandler;
    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .baseUrl(appConfig.getFetchMessageUrl())
                .requestInterceptor(loggingInterceptor)
                .defaultStatusHandler(customResponseErrorHandler)
                .build();
    }
}
