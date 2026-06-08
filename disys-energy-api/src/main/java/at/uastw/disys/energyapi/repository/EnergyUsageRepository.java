package at.uastw.disys.energyapi.repository;

import at.uastw.disys.energyapi.entity.EnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsage, LocalDateTime> {
    // Hier drin erbt Spring Boot automatisch alle Standardbefehle wie findById, save, etc.
}