package com.example.appelOff.Service;

import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Entity.Utilisateur;

import java.util.List;

public interface OffreService {

    Offre CreateOffreTagsUser(Offre offre, List<String> tagNames, String creator);
}
