package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.extensions.models.Extension;
import org.springframework.data.repository.CrudRepository;

public interface ExtensionRepository extends CrudRepository<Extension,Long> {

}
