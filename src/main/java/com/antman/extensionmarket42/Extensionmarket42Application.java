package com.antman.extensionmarket42;

import com.antman.extensionmarket42.utils.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Extensionmarket42Application {

    public static void main(String[] args) {
       SpringApplication.run(Extensionmarket42Application.class, args);
    }
}
