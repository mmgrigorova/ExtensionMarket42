package com.antman.extensionmarket42.restcontrollers;

import com.antman.extensionmarket42.repositories.base.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagRepository tagRepository;

    @Autowired
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("tags")
    public Set<String> availableTags(){
        Set<String> tags = new HashSet<>();

        tagRepository.findAll().forEach(tag -> tags.add(tag.getTagTitle()));

        return tags;
    }
}
