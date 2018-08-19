package com.antman.extensionmarket42.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({"com.antman.extensionmarket42.repositories.base",
        "com.antman.extensionmarket42.extensions.repositories.base"})
public class JPAConfig {
}
