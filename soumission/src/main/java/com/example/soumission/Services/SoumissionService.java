package com.example.soumission.Services;

import com.example.soumission.Entity.Soumission;

import java.util.List;
import java.util.Map;

public interface SoumissionService {

    Soumission addSoumission(Soumission soumission);

    List<Soumission> getSoumissionsByOfferId(Long offerId);
    Long countSoumissionsByOfferId(Long offerId);
    Map<String, Long> getDailySoumissionCountForOfferLast7Days(Long offerId);
}
