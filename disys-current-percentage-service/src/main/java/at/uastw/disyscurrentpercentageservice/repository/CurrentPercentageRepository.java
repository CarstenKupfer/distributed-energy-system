package at.uastw.disyscurrentpercentageservice.repository;

import at.uastw.disyscurrentpercentageservice.entity.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, LocalDateTime> {
}
