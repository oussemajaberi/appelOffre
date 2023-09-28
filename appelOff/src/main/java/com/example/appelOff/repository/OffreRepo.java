package com.example.appelOff.repository;

import com.example.appelOff.Entity.Categories;
import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OffreRepo extends JpaRepository<Offre, Long> {
    List<Offre> findByCategorie(Categories category);
    List<Offre> findByDateCreationBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT o FROM Offre o " +
            "JOIN o.tags t " +
            "WHERE t.name IN :tagNames " +
            "AND o.categorie = :categorie " +
            "AND o.id <> :offerId")
    List<Offre> findSimilarOffers(Long offerId, List<String> tagNames, Categories categorie);
    Long countByCreateur(Utilisateur user);
    List<Offre> findByCreateur(Utilisateur createur);
    Long countByCreateurIdAndDateCreationBetween(String createurId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
