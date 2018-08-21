package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
    User findByUsername(String email);
}
