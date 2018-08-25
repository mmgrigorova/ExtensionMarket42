package com.antman.extensionmarket42.dtos;

import com.antman.extensionmarket42.dtos.repositorydtos.PullRequest;

import java.util.ArrayList;
import java.util.List;

public class RepositoryPullRequestsDto {
    private List<PullRequest> pullRequests;

    public RepositoryPullRequestsDto() {
        pullRequests = new ArrayList<>();
    }

    public List<PullRequest> getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(List<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }
}
