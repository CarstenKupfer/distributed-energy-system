package at.uastw.disysenergyproducer.service;


import at.uastw.disysenergyproducer.dto.EnergyMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final WeatherService weatherService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public ProducerService (RabbitTemplate rabbitTemplate, WeatherService weatherService){
        this.rabbitTemplate = rabbitTemplate;
        this.weatherService = weatherService;
    }

    @Scheduled(fixedDelay = 3000)
    public void sendEnergyMessage() throws JsonProcessingException {

        double cloudCover = weatherService.getCloudCover();

        double kwh;

        if (cloudCover < 25) {

            // sunny
            kwh = ThreadLocalRandom.current()
                    .nextDouble(0.006, 0.010);

        } else if (cloudCover < 75) {

            // partly cloudy
            kwh = ThreadLocalRandom.current()
                    .nextDouble(0.003, 0.007);

        } else {

            // heavily cloudy
            kwh = ThreadLocalRandom.current()
                    .nextDouble(0.001, 0.004);
        }

        System.out.println("Cloud cover: " + cloudCover + "%");

        EnergyMessageDto message = new EnergyMessageDto(
                "PRODUCER",
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
