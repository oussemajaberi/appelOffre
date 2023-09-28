package com.example.appelOff.Client;

import com.example.appelOff.Dto.SoumissionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "soumission", url = "http://localhost:8070")
public interface SoumissionClient {
    @GetMapping("/soumission/countByOfferId/{offerId}")
    Long countSoumissionsByOfferId(@PathVariable("offerId") Long offerId);

    @GetMapping("/soumission/dailysoumissionCount/{offerId}")
    Map<String, Long> getDailySoumissionCountForOfferLast7Days(@PathVariable("offerId") Long offerId);

    @GetMapping("/soumissions/offer/{offerId}")
    List<SoumissionDTO> getSoumissionsByOfferId(@PathVariable("offerId") Long offerId);
}
