package com.example.soumission.Services;

import com.example.soumission.Entity.Soumission;
import com.example.soumission.Repo.SoumissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SoumissionServiceImp implements SoumissionService{

    @Autowired
    SoumissionRepo soumissionRepo;

    @Override
    public Soumission addSoumission(Soumission soumission) {
        return soumissionRepo.save(soumission);
    }

    @Override

    public List<Soumission> getSoumissionsByOfferId(Long offerId) {
        return soumissionRepo.findByOffreId(offerId);
    }

    @Override
    public Long countSoumissionsByOfferId(Long offerId) {
        return soumissionRepo.countByOffreId(offerId);
    }



    @Override
    public Map<String, Long> getDailySoumissionCountForOfferLast7Days(Long offerId) {
        Map<String, Long> dailySoumissionCounts = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 6; i >= 0; i--) {  // Loop for the last 7 days
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);

            long soumissionCount = soumissionRepo.countByOffreIdAndDateCreationBetween(offerId, startOfDay, endOfDay);
            dailySoumissionCounts.put(date.toString(), soumissionCount);
        }

        return dailySoumissionCounts;
    }


}
