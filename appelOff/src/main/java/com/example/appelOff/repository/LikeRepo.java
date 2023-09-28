package com.example.appelOff.repository;

import com.example.appelOff.Entity.Like;
import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepo extends JpaRepository<Like, Long> {
    Like findByUserAndOffer(Utilisateur user, Offre offer);
}
