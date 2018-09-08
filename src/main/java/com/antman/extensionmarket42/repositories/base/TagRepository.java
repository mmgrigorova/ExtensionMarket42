package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findTagByTagTitle(String tagTitle);
}
