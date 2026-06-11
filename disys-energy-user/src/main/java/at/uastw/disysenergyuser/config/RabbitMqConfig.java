package at.uastw.disysenergyuser.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue energyMessagesQueue(){
        return new Queue("energy_messages", true);
    }
}
