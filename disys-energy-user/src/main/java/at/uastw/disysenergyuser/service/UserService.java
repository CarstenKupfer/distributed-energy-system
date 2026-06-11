package at.uastw.disysenergyuser.service;

import at.uastw.disysenergyuser.dto.EnergyMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public UserService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 3000)
    public void sendEnergyMessage() throws JsonProcessingException{

        int hour = LocalDateTime.now().getHour();

        double kwh;

        // morning
        if(hour >= 6 && hour < 9){
            kwh = ThreadLocalRandom.current().nextDouble(0.004, 0.008);
        }

        //evening
        else if (hour >= 17 && hour < 22){
            kwh = ThreadLocalRandom.current().nextDouble(0.004, 0.008);
        }

        //night
        else if(hour >= 22 || hour < 6){
            kwh = ThreadLocalRandom.current().nextDouble(0.001, 0.003);
        }

        // normal daily consumption
        else {
            kwh = ThreadLocalRandom.current().nextDouble(0.002, 0.005);
        }

        EnergyMessageDto message = new EnergyMessageDto(
                "USER",
                "COMMUNITY",
                kwh,
                LocalDateTime.now()
        );

        String json = objectMapper.writeValueAsString(message);

        rabbitTemplate.convertAndSend(
                "energy_messages",
                json
        );

        System.out.println(json);
    }
}