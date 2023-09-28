package com.example.appelOff.Service;

import com.example.appelOff.Entity.Utilisateur;
import com.example.appelOff.repository.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImp implements UtilisateurService {
    @Autowired
    UtilisateurRepo utilisateurRepo;


    @Override
    public Utilisateur getUserById(String userId) {
        return utilisateurRepo.findById(userId).orElse(null);
    }

    @Override
    public Utilisateur updateUser(String userId, Utilisateur updatedUser) {
        Utilisateur existingUser = utilisateurRepo.findById(userId).orElse(null);
        if (existingUser != null) {
            // Update user properties with the provided data
            existingUser.setFirst_name(updatedUser.getFirst_name());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            // Add other properties as needed

            // Save the updated user to the database
            return utilisateurRepo.save(existingUser);
        }
        return null;
    }
}
