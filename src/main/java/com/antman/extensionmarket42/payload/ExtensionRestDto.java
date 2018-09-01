package com.antman.extensionmarket42.payload;

import java.util.Set;

public class ExtensionRestDto {
    private Long extensionId;
    private String name;
    private String description;
    private String ownerName;
    private String version;
    private int downloadCount;
    private String repoLink;
    private int openIssues;
    private int pullRequests;
    private String lastCommitDate;
    private String fileName;
    private Set<String> tags;

    public ExtensionRestDto() {
    }

    public ExtensionRestDto(Long extensionId, String name, String description, String ownerFirstName, String ownerLastName, String version, int downloadCount, String repoLink, int openIssues, int pullRequests, String lastCommitDate, String fileName, Set<String> tags) {
        this.extensionId = extensionId;
        this.name = name;
        this.description = description;
        setOwnerName(ownerFirstName, ownerLastName);
        this.version = version;
        this.downloadCount = downloadCount;
        this.repoLink = repoLink;
        this.openIssues = openIssues;
        this.pullRequests = pullRequests;
        this.lastCommitDate = lastCommitDate;
        this.fileName = fileName;
        this.tags = tags;
    }

    public Long getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(Long extensionId) {
        this.extensionId = extensionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerFirstName, String ownerLastName) {
        this.ownerName = ownerFirstName + " " + ownerLastName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
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

    public String getLastCommitDate() {
        return lastCommitDate;
    }

    public void setLastCommitDate(String lastCommitDate) {
        this.lastCommitDate = lastCommitDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
