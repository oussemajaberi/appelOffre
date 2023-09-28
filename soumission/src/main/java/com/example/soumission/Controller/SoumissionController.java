package com.example.soumission.Controller;

import com.example.soumission.Client.OfferClient;
import com.example.soumission.Dto.OfferDTO;
import com.example.soumission.Entity.Soumission;
import com.example.soumission.Services.SoumissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/soumission")
public class SoumissionController {
    @Autowired
    SoumissionService soumissionService;
    @Autowired
    OfferClient offerClient;

    @PostMapping("/add/{offreId}")
    public ResponseEntity<Soumission> addSoumissionToOffre(
            @PathVariable("offreId") Long offreId,
            @RequestBody Soumission soumission
    ) {
        OfferDTO offre = offerClient.getOfferById(offreId);
        if (offre == null) {
            return ResponseEntity.notFound().build();
        }

        soumission.setOffreId(offreId);
        Soumission addedSoumission = soumissionService.addSoumission(soumission);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedSoumission);
    }
    @GetMapping("/countByOfferId/{offerId}")
    public Long countSoumissionsByOfferId(@PathVariable Long offerId) {
        return soumissionService.countSoumissionsByOfferId(offerId);
    }

    @GetMapping("/dailysoumissionCount/{offerId}")
    public ResponseEntity<Map<String, Long>> getDailyOfferCountForUserLast7Days(@PathVariable Long offerId) {
        Map<String, Long> dailyOfferCounts = soumissionService.getDailySoumissionCountForOfferLast7Days(offerId);
        return ResponseEntity.ok(dailyOfferCounts);
    }
}
