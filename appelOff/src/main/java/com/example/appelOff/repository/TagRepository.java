package com.example.appelOff.repository;

import com.example.appelOff.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository  extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    List<Tag> findByNameStartsWithIgnoreCase(String tagPrefix);
}
