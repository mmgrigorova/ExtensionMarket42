package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.repositorydtos.Repo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GitHubServiceImpl implements GitHubService{
    private final String OPEN_ISSUES_COUNT_URL = "https://api.github.com/repos/circleci/circleci-images";
    private final String PULL_REQUESTS_COUNT_URL = "https://api.github.com/repos/circleci/circleci-images/pulls";
    private final String LAST_COMMIT_DATE_URL = "https://api.github.com/repos/mmgrigorova/dogswithbenefits/commits";


    @Override
    public int getRepositoryInfo() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        Repo repo = restTemplate.getForObject(OPEN_ISSUES_COUNT_URL, Repo.class);

        return repo.getOpenIssuesCount();
    }
}
