package at.uastw.disysusageservice.service;


import at.uastw.disysusageservice.dto.EnergyMessageDto;
import at.uastw.disysusageservice.dto.UsageUpdatedMessageDto;
import at.uastw.disysusageservice.entity.EnergyUsage;
import at.uastw.disysusageservice.repository.EnergyUsageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsageService {

    private final EnergyUsageRepository energyUsageRepository;
    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    public UsageService(EnergyUsageRepository energyUsageRepository, RabbitTemplate rabbitTemplate) {
        this.energyUsageRepository = energyUsageRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "energy_messages")
    public void processEnergyMessage(String json) throws Exception {
        EnergyMessageDto message = objectMapper.readValue(json, EnergyMessageDto.class);

        LocalDateTime hour = message.getDatetime()
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        EnergyUsage usage = energyUsageRepository.findById(hour)
                .orElse(new EnergyUsage(hour, 0.0, 0.0, 0.0));

        if ("PRODUCER".equals(message.getType())) {
            usage.setCommunityProduced(
                    usage.getCommunityProduced() + message.getKwh()
            );
        }

        if ("USER".equals(message.getType())) {
            double availableCommunityEnergy =
                    usage.getCommunityProduced() - usage.getCommunityUsed();

            double communityPart = Math.min(
                    message.getKwh(),
                    Math.max(availableCommunityEnergy, 0.0)
            );

            double gridPart = message.getKwh() - communityPart;

            usage.setCommunityUsed(
                    usage.getCommunityUsed() + communityPart
            );

            usage.setGridUsed(
                    usage.getGridUsed() + gridPart
            );
        }

        energyUsageRepository.save(usage);

        UsageUpdatedMessageDto updatedMessage = new UsageUpdatedMessageDto(hour);

        String updateJson = objectMapper.writeValueAsString(updatedMessage);

        rabbitTemplate.convertAndSend(
                "usage_updated",
                updateJson
        );

        System.out.println("Processed message: " + json);
        System.out.println("Updated hour: " + hour);

    }
}
