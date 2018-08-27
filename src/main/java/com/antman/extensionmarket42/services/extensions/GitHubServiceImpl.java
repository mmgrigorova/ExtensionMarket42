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
    private final String ACCESS_TOKEN = "d07e8c83def2460ae996948d7d0d7ac16777c358";

    @Override
    public RepositoryDto getRepositoryInfo(String gitUser, String repoName) throws IOException, ParseException {

        GitHub gitHub = null;
        try {
            gitHub = GitHub.connectUsingOAuth(ACCESS_TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
            long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
            return new RepositoryDto(0, 0, new Date(epoch));
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
