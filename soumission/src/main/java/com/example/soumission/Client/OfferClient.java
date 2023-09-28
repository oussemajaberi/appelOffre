package com.example.soumission.Client;


import com.example.soumission.Dto.OfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clients", url = "http://localhost:8086")
public interface OfferClient {
    @GetMapping("/offre/{offerId}")
    OfferDTO getOfferById(@PathVariable("offerId") Long offerId);
}
