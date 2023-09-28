package com.example.appelOff.Controller;

import com.example.appelOff.Entity.Utilisateur;
import com.example.appelOff.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/{userId}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable String userId) {
        Utilisateur user = utilisateurService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/modif/{userId}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable String userId, @RequestBody Utilisateur updatedUser) {
        Utilisateur user = utilisateurService.updateUser(userId, updatedUser);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
