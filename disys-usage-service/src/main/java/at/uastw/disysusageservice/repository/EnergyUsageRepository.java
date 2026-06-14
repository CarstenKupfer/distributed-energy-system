package at.uastw.disysusageservice.repository;

import at.uastw.disysusageservice.entity.EnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsage, LocalDateTime> {
}
