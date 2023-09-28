package com.example.appelOff.Service;

import com.example.appelOff.Client.SoumissionClient;
import com.example.appelOff.Dto.SoumissionDTO;
import com.example.appelOff.Entity.*;
import com.example.appelOff.repository.LikeRepo;
import com.example.appelOff.repository.OffreRepo;
import com.example.appelOff.repository.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OffreServiceImpl implements OffreService {
    @Autowired
    OffreRepo offreRepo;
    @Autowired
    TagServiceImpl tagService;
    @Autowired
    UtilisateurRepo utilisateurRepo;
    @Autowired
    LikeRepo likeRepo;
    @Autowired
     SoumissionClient soumissionClient;


    @Override
    public Offre CreateOffreTagsUser(Offre offre, List<String> tagNames, String creator, Categories categories) {
        Utilisateur utilisateur=utilisateurRepo.findById((creator)).orElse(null);
        if (creator == null) {
            // Handle if creator is not found
            throw new IllegalArgumentException("Creator not found");
        }
        offre.setCreateur(utilisateur);
        offre.setCategorie(categories);
        offre.setStatus(Status.OPEN);

        // Add the tags to the offer
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag tag = tagService.findTagByName(tagName);
            if (tag != null) {
                tags.add(tag);
            } else {
                // Create a new tag if it doesn't exist
                tag = new Tag();
                tag.setName(tagName);
                tag = tagService.SaveTag(tag);
                tags.add(tag);
            }
        }
        offre.setTags(tags);
        return offreRepo.save(offre);
    }

    @Override
    public Offre getOffreById(Long offreId) {

        return offreRepo.findById(offreId)
                .orElseThrow(() -> new EntityNotFoundException("Offre not found with id: " + offreId));
    }

    @Override
    public Offre updateOffre(Long offerId, Offre updatedOffre, String userId) {
        // Get the user from the database using the provided userId
        Utilisateur user = utilisateurRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Get the existing offer from the database
        Offre existingOffre = offreRepo.findById(offerId).orElse(null);
        if (existingOffre == null) {
            throw new EntityNotFoundException("Offer not found with id: " + offerId);
        }

        // Check if the user is the creator of the offer
        if (!user.getId().equals(existingOffre.getCreateur().getId())) {
            throw new IllegalArgumentException("User is not the creator of this offer.");
        }

        // Update the fields of the existing offer with the values from the updatedOffre
        existingOffre.setTitre(updatedOffre.getTitre());
        existingOffre.setDescription(updatedOffre.getDescription());
        existingOffre.setCategorie(updatedOffre.getCategorie()); // Update the categorie field

        // Update any other fields you want to allow modification for

        // Save the updated offer to the database
        return offreRepo.save(existingOffre);
    }





    @Override
    public List<Offre> getAllOffres() {
        return offreRepo.findAll();
    }

    @Override
    public List<Offre> getOffreCraetedBy(Utilisateur creator) {
        return offreRepo.findByCreateur(creator);
    }

    @Override
    public List<Offre> getOffresByCategory(Categories category) {
        return offreRepo.findByCategorie(category);
    }

    @Override
    public List<Offre> getOffersCreatedToday() {
        // Implement the logic to get offers created today from the database
        LocalDateTime startOfToday = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.now().with(LocalTime.MAX);
        return offreRepo.findByDateCreationBetween(startOfToday, endOfToday);
    }

    @Override
    public List<Offre> getOffersCreatedThisWeek() {
        // Implement the logic to get offers created this week from the database
        LocalDateTime startOfThisWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.MIN);
        LocalDateTime endOfThisWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.MAX);

        return offreRepo.findByDateCreationBetween(startOfThisWeek, endOfThisWeek);
    }

    @Override
    public List<Offre> getOffersCreatedThisMonth() {
        // Implement the logic to get offers created this month from the database
        LocalDateTime startOfThisMonth = LocalDateTime.now().withDayOfMonth(1).with(LocalTime.MIN);
        LocalDateTime endOfThisMonth = LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).with(LocalTime.MAX);

        return offreRepo.findByDateCreationBetween(startOfThisMonth, endOfThisMonth);
    }

    @Override
    public List<Offre> findSimilarOffers(Long offerId) {
        Offre offre=offreRepo.findById(offerId).orElse(null);
        if(offre==null){
            return null;
        }
        List<String> TagNames=offre.getTagNames();
        Categories categorie=offre.getCategorie();

        return offreRepo.findSimilarOffers(offerId, TagNames, categorie);
    }

    @Override
    @Transactional
    public void likeOffer(Utilisateur user, Offre offer) {
        if (!userHasLikedOffer(user, offer)) {
            // Set a default value for likes if it is null
            Long likesCount = offer.getLikes() != null ? offer.getLikes() : 0L;

            // Increment the likes count for the offer
            offer.setLikes(likesCount + 1);
            offreRepo.save(offer);

            // Create a new Like entity and link it to the user and offer
            Like like = new Like();
            like.setUser(user);
            like.setOffer(offer);
            likeRepo.save(like);
        }
    }

    @Override
    @Transactional
    public void unlikeOffer(Utilisateur user, Offre offer) {
        Like existingLike = findLikeByUserAndOffer(user, offer);
        if (existingLike != null) {
            // Decrement the likes count for the offer
            Long likesCount = offer.getLikes() != null ? offer.getLikes() : 0L;
            offer.setLikes(Math.max(0, likesCount - 1));
            // Remove the like from the user's likesList
            user.getLikesList().remove(existingLike);

            // Update the user entity to remove the association with the Like entity
            existingLike.setUser(null);

            // Save the changes to the database
            offreRepo.save(offer);
            utilisateurRepo.save(user);
            likeRepo.delete(existingLike);
        }
    }


    @Override
    public Like findLikeByUserAndOffer(Utilisateur user, Offre offer) {
        return likeRepo.findByUserAndOffer(user, offer);
    }

    @Override
    public Utilisateur getUserById(String userId) {
        return utilisateurRepo.findById(userId).orElse(null);
    }

    @Override
    public Long getTotalOffersForUser(Utilisateur user) {
        return offreRepo.countByCreateur(user);
    }


    @Override
    public boolean userHasLikedOffer(Utilisateur user, Offre offer) {
        return user.getLikesList().stream()
                .anyMatch(like -> like.getOffer().getId().equals(offer.getId()));
    }


    @Override
    public Long countSoumissionsForOffre(Long offreId) {
        return soumissionClient.countSoumissionsByOfferId(offreId);
    }

    @Override
    public Long getTotalSoumissionsForUser(Utilisateur user) {
        // Get all offers created by the user
        List<Offre> userOffers = offreRepo.findByCreateur(user);

        // Initialize a variable to keep track of the total soumission count
        Long totalSoumissions = 0L;

        // Loop through the user's offers and get the soumission count for each offer using the soumissionClient
        for (Offre offre : userOffers) {
            Long soumissionCount = soumissionClient.countSoumissionsByOfferId(offre.getId());
            totalSoumissions += soumissionCount;
        }

        return totalSoumissions;
    }
    @Override
    public double getAverageSoumissionsForUser(String userId) {
        Utilisateur user = getUserById(userId);

        if (user == null) {
            return 0.0;
        }

        Long totalOffers = getTotalOffersForUser(user);
        if (totalOffers == 0) {
            return 0.0;
        }

        Long totalSoumissions = 0L;
        List<Offre> userOffers = offreRepo.findByCreateur(user);
        for (Offre offre : userOffers) {
            totalSoumissions += soumissionClient.countSoumissionsByOfferId(offre.getId());
        }

        return (double) totalSoumissions / totalOffers;
    }


    @Override
    public Map<String, Long> getDailyOfferCountForUserLast7Days(String userId) {
        Map<String, Long> dailyOfferCounts = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 6; i >= 0; i--) {  // Loop for the last 7 days
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);

            long offerCount = offreRepo.countByCreateurIdAndDateCreationBetween(userId, startOfDay, endOfDay);
            dailyOfferCounts.put(date.toString(), offerCount);
        }

        return dailyOfferCounts;
    }

    @Override
    public Map<String, Long> getDailySoumissionCountForOfferAndUserLast7Days(Long offerId, String userId) {
        return null;
    }


    //hethi bch njib beha les soumissions eli jeew luser al les offres mteou
    @Override
    public List<SoumissionDTO> getSoumissionsForUserOffers(String userId) {
        return null;
    }



}



