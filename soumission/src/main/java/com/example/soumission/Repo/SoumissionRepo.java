package com.example.soumission.Repo;

import com.example.soumission.Entity.Soumission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SoumissionRepo extends JpaRepository<Soumission, Long> {
    List<Soumission> findByOffreId(Long offreId);
    Long countByOffreId(Long offreId);
    Long countByOffreIdAndDateCreationBetween(Long offreId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
