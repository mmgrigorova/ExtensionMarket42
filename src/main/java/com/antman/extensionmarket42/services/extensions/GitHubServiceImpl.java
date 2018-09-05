package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GitHubServiceImpl implements RemoteRepositoryService {
    private final String ACCESS_TOKEN = "9192eb637704bccfcca966001c9f2f502eb81255";
    private final String REPOSITORY_LINK_BASE = "www.github.com/";

    @Override
    public RepositoryDto getRepositoryInfoByRepoData(String gitUser, String repoName) throws IOException {
        String repositoryDetails = generateRepositoryDetails(gitUser, repoName);
        return getRepositoryInfo(repositoryDetails);
    }
    @Override
    public RepositoryDto getRepositoryInfoByRepoLink(String repositoryLink) throws IOException {
        String repositoryDetails = extractRepositoryDetailsFromLink(repositoryLink);
        return getRepositoryInfo(repositoryDetails);
    }

    /**
     * In order to collect GitHub information, we use a wrapper API that gives us easy access to the requested data.
     * It requires the username and repository name to connect to GitHub.
     * @param repositoryDetails
     * @return RepositoryDto
     * @throws IOException
     */
    private RepositoryDto getRepositoryInfo(String repositoryDetails) throws IOException {

        GitHub gitHub = null;
        try {
            gitHub = GitHub.connectUsingOAuth(ACCESS_TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("A connection to the repository cannot be established at this time. Reason: " + e.getMessage());
        }

        GHRepository repo = gitHub.getRepository(repositoryDetails);

        String fullRepositoryLink = generateFullRepositoryLinkFromRepoDetails(repositoryDetails);
        int openIssuesCount = repo.getOpenIssueCount();
        int pullRequestsCount = repo.getPullRequests(GHIssueState.ALL).size();
        List<GHCommit> commits =  repo.listCommits().asList();
        Date lastCommitDate = commits.get(0).getCommitDate();

        return new RepositoryDto(openIssuesCount,pullRequestsCount,lastCommitDate, fullRepositoryLink);
    }

    private String generateRepositoryDetails(String username, String repoName){
        return username + "/" + repoName;
    }

    private String generateFullRepositoryLinkFromRepoDetails(String repoDetails){
        return REPOSITORY_LINK_BASE + repoDetails;
    }

    private String extractRepositoryDetailsFromLink(String fullRepositoryLink){
        return fullRepositoryLink.replace(REPOSITORY_LINK_BASE,"");
    }
}
