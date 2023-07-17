package com.example.appelOff.Service;

import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Entity.Tag;
import com.example.appelOff.Entity.Utilisateur;
import com.example.appelOff.repository.OffreRepo;
import com.example.appelOff.repository.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffreServiceImpl implements OffreService {
    @Autowired
    OffreRepo offreRepo;
    @Autowired
    TagServiceImpl tagService;
    @Autowired
    UtilisateurRepo utilisateurRepo;

    @Override
    public Offre CreateOffreTagsUser(Offre offre, List<String> tagNames, String creator) {
        Utilisateur utilisateur=utilisateurRepo.findById((creator)).orElse(null);
        if (creator == null) {
            // Handle if creator is not found
            throw new IllegalArgumentException("Creator not found");
        }
        offre.setCreateur(utilisateur);
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

}
