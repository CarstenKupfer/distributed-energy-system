package at.uastw.disys.energyapi.messaging;

import at.uastw.disys.energyapi.entity.CurrentPercentage;
import at.uastw.disys.energyapi.entity.EnergyUsage;
import at.uastw.disys.energyapi.repository.CurrentPercentageRepository;
import at.uastw.disys.energyapi.repository.EnergyUsageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Wichtig für LocalDateTime!
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnergyMessagingService {

    private final EnergyUsageRepository energyUsageRepository;
    private final CurrentPercentageRepository currentPercentageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Konstruktor
    public EnergyMessagingService(EnergyUsageRepository energyUsageRepository,
                                  CurrentPercentageRepository currentPercentageRepository) {
        this.energyUsageRepository = energyUsageRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }


    public record EnergyUsageMsg(LocalDateTime hour, double communityProduced, double communityUsed, double gridUsed) {}
    public record CurrentPercentageMsg(LocalDateTime hour, double communityDepleted, double gridPortion) {}

    @RabbitListener(queues = "energy_usage")
    public void readFromEnergyUsage(String message) throws Exception {
        System.out.println("Received message from queue");
        System.out.println(message);

        EnergyUsageMsg msg = objectMapper.readValue(message, EnergyUsageMsg.class);

        EnergyUsage entity = new EnergyUsage(
                msg.hour(),
                msg.communityProduced(),
                msg.communityUsed(),
                msg.gridUsed()
        );
        energyUsageRepository.save(entity);
    }

    @RabbitListener(queues = "current_percentage")
    public void readFromCurrentPercentage(String message) throws Exception {
        System.out.println("Received message from queue");
        System.out.println(message);

        CurrentPercentageMsg msg = objectMapper.readValue(message, CurrentPercentageMsg.class);

        CurrentPercentage entity = new CurrentPercentage(
                msg.hour(),
                msg.communityDepleted(),
                msg.gridPortion()
        );
        currentPercentageRepository.save(entity);
    }
}
