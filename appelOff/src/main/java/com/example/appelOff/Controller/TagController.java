package com.example.appelOff.Controller;


import com.example.appelOff.Entity.Tag;
import com.example.appelOff.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping("/add")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.SaveTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    }

    @GetMapping("/suggestions")
    public List<String> getTagSuggestions(@RequestParam("tagPrefix") String tagPrefix) {
        return tagService.getTagSuggestions(tagPrefix);
    }
}
