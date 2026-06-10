package at.uastw.disysenergyproducer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue energyMessagesQueue() {
        return new Queue("energy_messages", true);
    }
}
