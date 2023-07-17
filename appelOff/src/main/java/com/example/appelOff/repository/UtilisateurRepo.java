package com.example.appelOff.repository;

import com.example.appelOff.Entity.Tag;
import com.example.appelOff.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo extends JpaRepository<Utilisateur,String> {
}
