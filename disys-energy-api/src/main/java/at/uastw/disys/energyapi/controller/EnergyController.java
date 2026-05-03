package at.uastw.disys.energyapi.controller;

import at.uastw.disys.energyapi.dto.CurrentPercentageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyController {

    @GetMapping("/energy/current")
    public CurrentPercentageDto getCurrentEnergy() {
        return new CurrentPercentageDto(
                100.0,
                5.63
        );
    }
}
