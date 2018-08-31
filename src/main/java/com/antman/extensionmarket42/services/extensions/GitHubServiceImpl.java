package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private final String ACCESS_TOKEN = "9192eb637704bccfcca966001c9f2f502eb81255";

    @Override
    public RepositoryDto getRepositoryInfo(String gitUser, String repoName) throws IOException {

        GitHub gitHub = null;
        try {
            gitHub = GitHub.connectUsingOAuth(ACCESS_TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("A connection to the repository cannot be established at this time. Reason: " + e.getMessage());
        }

        String repoUrl = gitUser + "/" + repoName;
        GHRepository repo = gitHub.getRepository(repoUrl);

        int openIssuesCount = repo.getOpenIssueCount();
        int pullRequestsCount = repo.getPullRequests(GHIssueState.ALL).size();
        List<GHCommit> commits =  repo.listCommits().asList();
        Date lastCommitDate = commits.get(0).getCommitDate();

        return new RepositoryDto(openIssuesCount,pullRequestsCount,lastCommitDate);
    }
}
