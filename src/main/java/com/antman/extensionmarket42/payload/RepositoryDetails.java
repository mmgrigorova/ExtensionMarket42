package com.antman.extensionmarket42.payload;

import java.sql.Date;

public class RepositoryDetails {
    private String repoLink;

    private int openIssues;

    private int pullRequests;

    private Date lastCommit;

    public RepositoryDetails(String repoLink, int openIssues, int pullRequests, Date lastCommit) {
        this.repoLink = repoLink;
        this.openIssues = openIssues;
        this.pullRequests = pullRequests;
        this.lastCommit = lastCommit;
    }

    public String getRepoLink() {
        return repoLink;
    }

    public void setRepoLink(String repoLink) {
        this.repoLink = repoLink;
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
