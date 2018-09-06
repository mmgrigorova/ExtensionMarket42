package com.antman.extensionmarket42.payload;

import com.antman.extensionmarket42.models.extensions.Extension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositorySyncStatistics {
    private List<Extension> successfulExtensions;
    private List<Extension> failedExtensions;
    private Date lastSyncDate;

    public RepositorySyncStatistics() {
        successfulExtensions = new ArrayList<>();
        failedExtensions = new ArrayList<>();
    }

    public RepositorySyncStatistics(List<Extension> successfulExtensions, List<Extension> failedExtensions, Date lastSyncDate) {
        this.successfulExtensions = successfulExtensions;
        this.failedExtensions = failedExtensions;
        this.lastSyncDate = lastSyncDate;
    }

    public List<Extension> getSuccessfulExtensions() {
        return successfulExtensions;
    }

    public void setSuccessfulExtensions(List<Extension> successfulExtensions) {
        this.successfulExtensions = successfulExtensions;
    }

    public List<Extension> getFailedExtensions() {
        return failedExtensions;
    }

    public void addSuccessfull(Extension extension){
        successfulExtensions.add(extension);
    }

    public void setFailedExtensions(List<Extension> failedExtensions) {
        this.failedExtensions = failedExtensions;
    }

    public void addFailed(Extension extension){
        failedExtensions.add(extension);
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
}
