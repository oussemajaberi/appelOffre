package com.example.appelOff.Service;

import com.example.appelOff.Dto.SoumissionDTO;
import com.example.appelOff.Entity.Categories;
import com.example.appelOff.Entity.Like;
import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Entity.Utilisateur;

import java.util.List;
import java.util.Map;

public interface OffreService {

    Offre CreateOffreTagsUser(Offre offre, List<String> tagNames, String creator, Categories categories);
    Offre getOffreById(Long offreId);
    Offre updateOffre(Long offerId, Offre updatedOffre, String userId);
    List<Offre> getAllOffres();
    List<Offre>getOffreCraetedBy(Utilisateur creator);
    List<Offre> getOffresByCategory(Categories category);
    List<Offre> getOffersCreatedToday();
    List<Offre> getOffersCreatedThisWeek();
    List<Offre> getOffersCreatedThisMonth();
    List<Offre> findSimilarOffers(Long offerId);
    void likeOffer(Utilisateur user, Offre offer);
    void unlikeOffer(Utilisateur user, Offre offer);
    boolean userHasLikedOffer(Utilisateur user, Offre offer);
    Like findLikeByUserAndOffer(Utilisateur user, Offre offer);
    Utilisateur getUserById(String userId);
    Long getTotalOffersForUser(Utilisateur user);
    Long countSoumissionsForOffre(Long offreId);
    Long getTotalSoumissionsForUser(Utilisateur user);
    double getAverageSoumissionsForUser(String userId);
    Map<String, Long> getDailyOfferCountForUserLast7Days(String userId);
    Map<String, Long> getDailySoumissionCountForOfferAndUserLast7Days(Long offerId, String userId);
    List<SoumissionDTO> getSoumissionsForUserOffers(String userId);
}
