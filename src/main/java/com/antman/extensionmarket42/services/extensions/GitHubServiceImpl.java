package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.dtos.RepositoryPullRequestsDto;
import com.antman.extensionmarket42.dtos.repositorydtos.Commit;
import com.antman.extensionmarket42.dtos.repositorydtos.PullRequest;
import com.antman.extensionmarket42.dtos.repositorydtos.Repo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private final String PULL_REQUESTS_COUNT_URI = "/pulls";
    private final String LAST_COMMIT_DATE_URI = "/commits/master";


    @Override
    public RepositoryDto getRepositoryInfo(String repoUrl) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        Repo repo = restTemplate.getForObject(repoUrl, Repo.class);
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
//
//        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(commitsUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Commit>>() {
//                });
//        List<Commit> commitsList = commitsResponse.getBody();

        Commit lastCommit = restTemplate.getForObject(commitsUrl, Commit.class);

        Date date = new Date();
        java.sql.Date lastCommitDate = new java.sql.Date(date.getTime());

        RepositoryDto repositoryDto = new RepositoryDto(openIssues, pullRequests, lastCommitDate);

        return repositoryDto;
    }
}
