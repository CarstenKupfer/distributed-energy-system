package at.uastw.disys.energyapi.repository;

import at.uastw.disys.energyapi.entity.EnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsage, LocalDateTime> {

    List<EnergyUsage> findAllByOrderByHourAsc();
}