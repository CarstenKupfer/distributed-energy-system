package at.uastw.disys.energyapi.controller;

import at.uastw.disys.energyapi.dto.CurrentPercentageDto;
import at.uastw.disys.energyapi.dto.UsageDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @GetMapping("/current")
    public CurrentPercentageDto getCurrentEnergy() {
        return new CurrentPercentageDto(
                100.0,
                5.63
        );
    }

    @GetMapping("/historical")
    public List<UsageDto> getHistoricalEnergy(
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end
    ) {
        List<UsageDto> data = List.of(
                new UsageDto(LocalDateTime.of(2026, 5, 4, 14, 0), 18.05, 18.05, 1.076),
                new UsageDto(LocalDateTime.of(2026, 5, 4, 13, 0), 15.015, 14.033, 2.049),
                new UsageDto(LocalDateTime.of(2026, 5, 4, 12, 0), 13.8, 12.7, 1.55)
        );
        return data.stream()
                .filter(d -> start == null||!d.getHour().isBefore(start))
                .filter(d -> end == null||!d.getHour().isAfter(end))
                .toList();
    }
}

