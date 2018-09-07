package com.antman.extensionmarket42.schedules;

import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataService;
import com.antman.extensionmarket42.utils.SystemTimeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Component
@ConfigurationProperties("application.properties")
public class ScheduledGitHubRefresh {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledGitHubRefresh.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private ExtensionRepositoryDataService extensionRepositoryDataService;

    @Autowired
    public ScheduledGitHubRefresh(ExtensionRepositoryDataService extensionRepositoryDataService, Environment environment) {
        this.extensionRepositoryDataService = extensionRepositoryDataService;
    }


    @Scheduled(fixedRateString = "${schedule.fixedRateTime}", initialDelayString = "${schedule.initialDelayTime}")
    public void refreshGitHubData() {
        logger.info("Running GitHub Refresh start at {}", dateFormat.format(new Date(new SystemTimeWrapper().currentTimeMillisSystem())));
        extensionRepositoryDataService.refreshRepositoryInfoAllActiveExtensions();
        logger.info("Running GitHub Refresh ending at {}", dateFormat.format(new Date(new SystemTimeWrapper().currentTimeMillisSystem())));
    }
}
