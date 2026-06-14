package at.uastw.disyscurrentpercentageservice.service;


import at.uastw.disyscurrentpercentageservice.dto.UsageUpdatedMessageDto;
import at.uastw.disyscurrentpercentageservice.entity.CurrentPercentage;
import at.uastw.disyscurrentpercentageservice.repository.CurrentPercentageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CurrentPercentageService {

    private final CurrentPercentageRepository currentPercentageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public CurrentPercentageService(CurrentPercentageRepository currentPercentageRepository){
        this.currentPercentageRepository = currentPercentageRepository;
    }

    @RabbitListener(queues = "usage_updated")
    public void processUsageUpdate(String json) throws Exception {

        UsageUpdatedMessageDto message = objectMapper.readValue(json, UsageUpdatedMessageDto.class);

        double communityDepleted = 0.0;
        double gridPortion = 0.0;

        if(message.getCommunityProduced() > 0){
            communityDepleted = (message.getCommunityUsed() / message.getCommunityProduced()) * 100.0;
        }

        double totalConsumption = message.getCommunityUsed() + message.getGridUsed();

        if (totalConsumption > 0){
            gridPortion = (message.getGridUsed() / totalConsumption) * 100.0;
        }

        CurrentPercentage currentPercentage = new CurrentPercentage(message.getHour(), communityDepleted, gridPortion);

        currentPercentageRepository.save(currentPercentage);

        System.out.println("Updated current percentage for " + message.getHour());
    }
}
