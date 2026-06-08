package at.uastw.disys.energyapi.controller;

import at.uastw.disys.energyapi.dto.CurrentPercentageDto;
import at.uastw.disys.energyapi.dto.UsageDto;
import at.uastw.disys.energyapi.entity.CurrentPercentage;
import at.uastw.disys.energyapi.entity.EnergyUsage;
import at.uastw.disys.energyapi.repository.CurrentPercentageRepository;
import at.uastw.disys.energyapi.repository.EnergyUsageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    // repositories für datenbank
    private final CurrentPercentageRepository currentPercentageRepository;
    private final EnergyUsageRepository energyUsageRepository;

    public EnergyController(CurrentPercentageRepository currentPercentageRepository, EnergyUsageRepository energyUsageRepository) {
        this.currentPercentageRepository = currentPercentageRepository;
        this.energyUsageRepository = energyUsageRepository;
    }

    @GetMapping("/current")
    public CurrentPercentageDto getCurrentEnergy() {
        List<CurrentPercentage> list = currentPercentageRepository.findAll();

        // falls datenbank leer ist
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No energy data available!");
        }

        CurrentPercentage latest = list.get(list.size() - 1);
        return mapCurrentToDto(latest);
    }

    @GetMapping("/historical")
    public List<UsageDto> getHistoricalEnergy(
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end
    ) {

        return energyUsageRepository.findAll().stream()
                .filter(d -> start == null || !d.getHour().isBefore(start))
                .filter(d -> end == null || !d.getHour().isAfter(end))
                .map(this::mapUsageToDto)
                .toList();
    }

    //dto für GUI
    private CurrentPercentageDto mapCurrentToDto(CurrentPercentage entity) {
        return new CurrentPercentageDto(
                entity.getCommunityDepleted(),
                entity.getGridPortion()
        );
    }
    // DTO für gui
    private UsageDto mapUsageToDto(EnergyUsage entity) {
        return new UsageDto(
                entity.getHour(),
                entity.getCommunityProduced(),
                entity.getCommunityUsed(),
                entity.getGridUsed()
        );
    }
}