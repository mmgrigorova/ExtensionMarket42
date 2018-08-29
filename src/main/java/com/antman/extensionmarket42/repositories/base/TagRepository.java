package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
}
