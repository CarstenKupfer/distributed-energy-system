package at.uastw.disyscurrentpercentageservice.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    @Bean
    public Queue usageUpdatedQueue(){
        return new Queue("usage_updated", true);
    }
}
