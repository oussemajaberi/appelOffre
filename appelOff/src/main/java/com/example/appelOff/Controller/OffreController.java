package com.example.appelOff.Controller;

import com.example.appelOff.Entity.Offre;
import com.example.appelOff.Service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offre")
public class OffreController {
    @Autowired
    OffreService offreService;


    @PostMapping("/add/{createdby}")
    @ResponseBody
    Offre AddOffre(@RequestBody Offre offre, @PathVariable("createdby") String creator) {
        List<String> tagNames = offre.getTagNames(); // Assuming you have a method to get the tag names from the offre object
        Offre createdOffre = offreService.CreateOffreTagsUser(offre, tagNames, creator);
        return createdOffre;
    }


}
