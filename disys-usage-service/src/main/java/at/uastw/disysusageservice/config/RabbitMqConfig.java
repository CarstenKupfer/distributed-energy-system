package at.uastw.disysusageservice.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue energyMessageQueue(){
        return new Queue("energy_messages", true);
    }

    @Bean
    public Queue usageUpdatedQueue(){
        return new Queue("usage_updated", true);
    }
}
