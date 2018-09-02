package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {
    Optional<User> findByUsername(String email);
    List<User> getAllByEnabled(boolean isEnabled);
}
