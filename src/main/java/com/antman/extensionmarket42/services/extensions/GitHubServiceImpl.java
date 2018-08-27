package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.dtos.RepositoryPullRequestsDto;
import com.antman.extensionmarket42.dtos.repositorydtos.*;
import com.antman.extensionmarket42.utils.SqlDateParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class GitHubServiceImpl implements GitHubService {
    private final String PULL_REQUESTS_COUNT_URI = "/pulls";
    private final String LAST_COMMIT_DATE_URI = "/commits/master";


    @Override
    public RepositoryDto getRepositoryInfo(String repoUrl) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();

        Repo repo = restTemplate.getForObject(repoUrl, Repo.class);
        assert repo != null;
        int openIssues = repo.getOpenIssues();

        String pullsUrl = repoUrl + PULL_REQUESTS_COUNT_URI;

        ResponseEntity<List<PullRequest>> pullRequestsResponse = restTemplate.exchange(pullsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PullRequest>>() {
                });

        List<PullRequest> pullRequestsList = pullRequestsResponse.getBody();

        assert pullRequestsList != null;
        int pullRequests = pullRequestsList.size();

        String commitsUrl = repoUrl + LAST_COMMIT_DATE_URI;

        CommitResponse lastCommit = restTemplate.getForObject(commitsUrl, CommitResponse.class, Author.class);

        assert lastCommit != null;
        String lastCommitDateString = lastCommit.getCommit().getAuthor().getDate();

        java.sql.Date lastCommitDate = SqlDateParser.parseSqlDateISO8601(lastCommitDateString);

        return new RepositoryDto(openIssues, pullRequests, lastCommitDate);
    }
}
