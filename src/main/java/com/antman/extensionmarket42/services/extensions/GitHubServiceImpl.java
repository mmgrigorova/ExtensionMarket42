package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.dtos.repositorydtos.*;
import com.antman.extensionmarket42.utils.SqlDateParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private final String PULL_REQUESTS_COUNT_URI = "/pulls";
    private final String LAST_COMMIT_DATE_URI = "/commits/master";


    @Override
    public RepositoryDto getRepositoryInfoFromRest(String repoUrl) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();

        Repo repo = restTemplate.getForObject(repoUrl, Repo.class);
        int openIssues = 0;

        if (repo == null) {
            long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
            return new RepositoryDto(0, 0, new java.sql.Date(epoch));
        }

        openIssues = repo.getOpenIssues();

        String pullsUrl = repoUrl + PULL_REQUESTS_COUNT_URI;

        int pullRequests = getClosedPullRequestsCount(pullsUrl);

        String commitsUrl = repoUrl + LAST_COMMIT_DATE_URI;

        CommitResponse lastCommit = restTemplate.getForObject(commitsUrl, CommitResponse.class, Author.class);

        assert lastCommit != null;
        String lastCommitDateString = lastCommit.getCommit().getAuthor().getDate();

        java.sql.Date lastCommitDate = SqlDateParser.parseISO8601Date(lastCommitDateString);

        return new RepositoryDto(openIssues, pullRequests, lastCommitDate);
    }

    private int getClosedPullRequestsCount(String repoPullsUrl){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<PullRequest>> pullRequestsResponse = restTemplate.exchange(repoPullsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PullRequest>>() {
                });

        List<PullRequest> pullRequestsList = pullRequestsResponse.getBody();

        assert pullRequestsList != null;
        int pullRequests = pullRequestsList.size();
        return 0;
    }
}
