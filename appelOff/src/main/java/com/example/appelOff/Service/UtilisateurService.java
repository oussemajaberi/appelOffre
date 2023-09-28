package com.example.appelOff.Service;

import com.example.appelOff.Entity.Utilisateur;

public interface UtilisateurService {
    Utilisateur getUserById(String userId);
    Utilisateur updateUser(String userId, Utilisateur updatedUser);
}
