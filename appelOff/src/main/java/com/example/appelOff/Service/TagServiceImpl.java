package com.example.appelOff.Service;

import com.example.appelOff.Entity.Tag;
import com.example.appelOff.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    TagRepository tagRepository;
    @Override
    public Tag SaveTag(Tag tag) {
        return tagRepository.save(tag);
    }
    public Tag findTagByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Override
    public List<String> getTagSuggestions(String tagPrefix) {
        // Query the database to retrieve tags that match the tag prefix
        List<Tag> tags = tagRepository.findByNameStartsWithIgnoreCase(tagPrefix);

        // Extract the tag names from the list of tags
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        return tagNames;
    }
}
