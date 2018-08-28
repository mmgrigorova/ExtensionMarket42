package com.antman.extensionmarket42.dtos;

import java.util.Date;

public class RepositoryDto {
    private int openIssues;
    private int pullRequests;
    private Date lastCommit;

    public RepositoryDto(int openIssues, int pullRequests, Date lastCommit) {
        this.openIssues = openIssues;
        this.pullRequests = pullRequests;
        this.lastCommit = lastCommit;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(int pullRequests) {
        this.pullRequests = pullRequests;
    }

    public Date getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(Date lastCommit) {
        this.lastCommit = lastCommit;
    }
}
