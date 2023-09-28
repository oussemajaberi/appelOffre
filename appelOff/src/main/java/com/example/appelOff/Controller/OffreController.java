package com.example.appelOff.Controller;

import com.example.appelOff.Entity.Categories;
import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Entity.Utilisateur;
import com.example.appelOff.Service.OffreService;
import com.example.appelOff.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offre")
public class OffreController {
    @Autowired
    OffreService offreService;
    @Autowired
    UtilisateurService utilisateurService;

    // Endpoint to get offers created today
    @GetMapping("/today")
    public ResponseEntity<List<Offre>> getOffersCreatedToday() {
        List<Offre> offers = offreService.getOffersCreatedToday();
        return ResponseEntity.ok(offers);
    }
    // Endpoint to get offers created this week
    @GetMapping("/thisWeek")
    public ResponseEntity<List<Offre>> getOffersCreatedThisWeek() {
        List<Offre> offers = offreService.getOffersCreatedThisWeek();
        return ResponseEntity.ok(offers);
    }

    // Endpoint to get offers created this month
    @GetMapping("/thisMonth")
    public ResponseEntity<List<Offre>> getOffersCreatedThisMonth() {
        List<Offre> offers = offreService.getOffersCreatedThisMonth();
        return ResponseEntity.ok(offers);
    }


    @PostMapping("/add/{createdby}")
    @ResponseBody
    Offre AddOffre(@RequestBody Offre offre, @PathVariable("createdby") String creator) {
        List<String> tagNames = offre.getTagNames(); // Assuming you have a method to get the tag names from the offre object
        Categories categorie=offre.getCategorie();
        Offre createdOffre = offreService.CreateOffreTagsUser(offre, tagNames, creator,categorie);
        return createdOffre;
    }

    @GetMapping("/{offreId}")
    public ResponseEntity<Offre> getOffreById(@PathVariable("offreId") Long offreId) {
        Offre offre = offreService.getOffreById(offreId);
        if (offre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(offre);
    }
    @GetMapping("/all")
    public List<Offre> getAllOffres() {
        return offreService.getAllOffres();
    }
    @GetMapping("/{offerId}/similar")
    public ResponseEntity<List<Offre>> getSimilarOffers(@PathVariable Long offerId) {
        List<Offre> similarOffers = offreService.findSimilarOffers(offerId);

        if (similarOffers == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(similarOffers);
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Offre>> getOffresByCategorie(@PathVariable Categories categorie) {
        List<Offre> offres = offreService.getOffresByCategory(categorie);
        if (offres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(offres, HttpStatus.OK);
        }
    }


    @PostMapping("/{offerId}/like/{userId}")
    public ResponseEntity<String> likeOffer(
            @PathVariable Long offerId,
            @PathVariable String userId
    ) {
        Offre offer = offreService.getOffreById(offerId);
        Utilisateur user = offreService.getUserById(userId);

        if (offer == null || user == null) {
            return ResponseEntity.notFound().build();
        }

        offreService.likeOffer(user, offer);
        return ResponseEntity.ok("{\"message\": \"Offer liked successfully.\"}");
    }

    @DeleteMapping("/{offerId}/like/{userId}")
    public ResponseEntity<String> unlikeOffer(
            @PathVariable Long offerId,
            @PathVariable String userId
    ) {
        Offre offer = offreService.getOffreById(offerId);
        Utilisateur user = offreService.getUserById(userId);

        if (offer == null || user == null) {
            return ResponseEntity.notFound().build();
        }

        offreService.unlikeOffer(user, offer);
        return ResponseEntity.ok("{\"message\": \"Offer unliked successfully.\"}");
    }
    @GetMapping("/{offerId}/like/{userId}")
    public ResponseEntity<Boolean> checkIfOfferLiked(
            @PathVariable Long offerId,
            @PathVariable String userId
    ) {
        Offre offer = offreService.getOffreById(offerId);
        Utilisateur user = offreService.getUserById(userId);

        if (offer == null || user == null) {
            return ResponseEntity.notFound().build();
        }

        boolean isLiked = offreService.userHasLikedOffer(user, offer);
        return ResponseEntity.ok(isLiked);
    }
    @GetMapping("/totalOffersPerUser/{userId}")
    public ResponseEntity<Long> getTotalOffersForUser(@PathVariable String userId) {
        Utilisateur user = offreService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long totalOffers = offreService.getTotalOffersForUser(user);
        return ResponseEntity.ok(totalOffers);
    }
    @GetMapping("/countSoumissions/{offreId}")
    public Long countSoumissionsForOffre(@PathVariable Long offreId) {
        return offreService.countSoumissionsForOffre(offreId);
    }

    @GetMapping("/totalSoumissions/{userId}")
    public ResponseEntity<Long> getTotalSoumissionsForUser(@PathVariable("userId") String userId) {
        Utilisateur user = utilisateurService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long totalSoumissions = offreService.getTotalSoumissionsForUser(user);
        return ResponseEntity.ok(totalSoumissions);
    }

    @GetMapping("/averageSoumissions/{userId}")
    public ResponseEntity<Double> getAverageSoumissionsForUser(@PathVariable String userId) {
        double averageSoumissions = offreService.getAverageSoumissionsForUser(userId);
        return ResponseEntity.ok(averageSoumissions);
    }

    @GetMapping("/getalloffre/{userId}")
    public ResponseEntity<List<Offre>> getOffreCraetedBy(@PathVariable String userId) {
        Utilisateur creator = offreService.getUserById(userId);
        if (creator == null) {
            return ResponseEntity.notFound().build();
        }

        List<Offre> offers = offreService.getOffreCraetedBy(creator);
        return ResponseEntity.ok(offers);
    }

    @PutMapping("/modify/{offerId}/{userId}")
    public ResponseEntity<Offre> updateOffre(
            @PathVariable Long offerId,
            @RequestBody Offre updatedOffre,
            @PathVariable String userId
    ) {
        Utilisateur user = utilisateurService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Offre updatedOffer = offreService.updateOffre(offerId, updatedOffre, userId);
            return ResponseEntity.ok(updatedOffer);
        } catch (EntityNotFoundException e) {
            // Handle the case where the offer is not found
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            // Handle the case where the user is not the creator of the offer
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/dailyOfferCount/{userId}")
    public ResponseEntity<Map<String, Long>> getDailyOfferCountForUserLast7Days(@PathVariable String userId) {
        Map<String, Long> dailyOfferCounts = offreService.getDailyOfferCountForUserLast7Days(userId);
        return ResponseEntity.ok(dailyOfferCounts);
    }



}


