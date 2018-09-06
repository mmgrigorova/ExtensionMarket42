package com.antman.extensionmarket42;

import com.antman.extensionmarket42.schedules.ScheduledGitHubRefresh;
import com.antman.extensionmarket42.utils.FileStorageProperties;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class,
        ScheduledGitHubRefresh.class
})
@EnableScheduling
public class Extensionmarket42Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Extensionmarket42Application.class);
    }

    public static void main(String[] args) {
       SpringApplication.run(Extensionmarket42Application.class, args);
    }
}
