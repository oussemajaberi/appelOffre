package com.example.appelOff.repository;

import com.example.appelOff.Entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepo extends JpaRepository<Offre, Long> {
}
