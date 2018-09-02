package com.antman.extensionmarket42;

import com.antman.extensionmarket42.utils.FileStorageProperties;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Extensionmarket42Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Extensionmarket42Application.class);
    }

    public static void main(String[] args) {
       SpringApplication.run(Extensionmarket42Application.class, args);
    }
}
