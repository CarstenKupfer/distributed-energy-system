package at.uastw.disysusageservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import at.uastw.disysusageservice.dto.EnergyMessageDto;
import at.uastw.disysusageservice.entity.EnergyUsage;
import at.uastw.disysusageservice.service.UsageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DisysUsageServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCalculateCommunityAndGridUsageCorrectly(){

        UsageService service = new UsageService(null, null);

        EnergyUsage usage = new EnergyUsage(
                LocalDateTime.of(2025, 1, 10, 14, 0),
                18.05,
                18.02,
                1.056
        );

        EnergyMessageDto message = new EnergyMessageDto(
                "USER",
                "COMMUNITY",
                0.05,
                LocalDateTime.now()
        );

        EnergyUsage result = service.applyMessage(usage, message);

        assertEquals(18.05, result.getCommunityProduced(), 0.0001);
        assertEquals(18.05, result.getCommunityUsed(), 0.0001);
        assertEquals(1.076, result.getGridUsed(), 0.0001);

    }
}
