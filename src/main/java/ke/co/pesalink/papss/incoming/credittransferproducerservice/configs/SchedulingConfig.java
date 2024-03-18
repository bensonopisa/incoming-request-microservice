package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.service.MessagePollerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class SchedulingConfig implements SchedulingConfigurer{
    private final MessagePollerService messagePollerService;

    private final AppConfig appConfig;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedRateTask(messagePollerService, appConfig.getPollerInterval());
    }
}
