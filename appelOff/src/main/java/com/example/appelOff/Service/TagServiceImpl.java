package com.example.appelOff.Service;

import com.example.appelOff.Entity.Tag;
import com.example.appelOff.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
