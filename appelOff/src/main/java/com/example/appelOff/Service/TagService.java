package com.example.appelOff.Service;

import com.example.appelOff.Entity.Tag;

import java.util.List;

public interface TagService {

    Tag SaveTag(Tag tag);
    List<String> getTagSuggestions(String tagPrefix);
}
