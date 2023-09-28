package com.example.soumission.Dto;

import lombok.Data;

import java.util.List;

@Data
public class OfferDTO {
    private Long id;
    private String titre;
    private String description;
    private UtilisateurDTO createur;
    private List<TagDTO> tags;
}