package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.service.PollerService;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.LoggerUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Arrays;
import java.util.Iterator;


@EnableScheduling
@Configuration
public class SchedulingConfig implements SchedulingConfigurer {
    private final AppConfig appConfig;
    private final PollerService pollerService;

    public SchedulingConfig(AppConfig appConfig, PollerService pollerService) {
        this.appConfig = appConfig;
        this.pollerService = pollerService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedRateTask(() -> {

            // get all the message requests from papss
            // do the signature validations
            //
            Iterable<Object> response =  pollerService.fetchMessages();

        }, appConfig.getPollerInterval());
    }
}
