package com.example.appelOff.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoumissionDTO {
    private Long id;
    private String titre;
    private String description;
    private Long offreId;

    // Constructor(s) if needed

    // Getters and Setters
}
