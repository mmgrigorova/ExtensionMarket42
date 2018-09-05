package com.antman.extensionmarket42.payload;

import com.antman.extensionmarket42.models.extensions.Extension;

import java.util.Date;
import java.util.List;

public class RepositorySyncStatistics {
    List<Extension> successfullExtensions;
    List<Extension> unnsuccessfulExtensions;
    Date lastSyncDate;

    public RepositorySyncStatistics() {
    }

    public RepositorySyncStatistics(List<Extension> successfullExtensions, List<Extension> unnsuccessfulExtensions, Date lastSyncDate) {
        this.successfullExtensions = successfullExtensions;
        this.unnsuccessfulExtensions = unnsuccessfulExtensions;
        this.lastSyncDate = lastSyncDate;
    }

    public List<Extension> getSuccessfullExtensions() {
        return successfullExtensions;
    }

    public void setSuccessfullExtensions(List<Extension> successfullExtensions) {
        this.successfullExtensions = successfullExtensions;
    }

    public List<Extension> getUnnsuccessfulExtensions() {
        return unnsuccessfulExtensions;
    }

    public void setUnnsuccessfulExtensions(List<Extension> unnsuccessfulExtensions) {
        this.unnsuccessfulExtensions = unnsuccessfulExtensions;
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
}
