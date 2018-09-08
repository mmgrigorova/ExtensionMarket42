package com.antman.extensionmarket42.models.repository;

import com.antman.extensionmarket42.models.extensions.Extension;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "data_refresh")
public class DataRefresh {

  @Id
  @Column
  private long id;
  @Column
  private java.sql.Timestamp lastRefreshDate;
  @Column
  private long successfulCount;
  @Column
  private long failedCount;
  @Transient
  private List<Extension> successfulExtensions;
  @Transient
  private List<Extension> failedExtensions;

  public DataRefresh() {
    successfulExtensions = new ArrayList<>();
    failedExtensions = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Timestamp getLastRefreshDate() {
    return lastRefreshDate;
  }

  public void setLastRefreshDate(long lastRefreshDate) {
    this.lastRefreshDate = new Timestamp(lastRefreshDate);
  }

  public long getSuccessfulCount() {
    return successfulCount;
  }

  public void setSuccessfulCount(long successfulCount) {
    this.successfulCount = successfulCount;
  }


  public long getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(long failedCount) {
    this.failedCount = failedCount;
  }

  public void setLastRefreshDate(Timestamp lastRefreshDate) {
    this.lastRefreshDate = lastRefreshDate;
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

  public void setFailedExtensions(List<Extension> failedExtensions) {
    this.failedExtensions = failedExtensions;
  }

  public void addSuccessfull(Extension extension) {
    successfulExtensions.add(extension);
  }

  public void addFailed(Extension extension) {
    failedExtensions.add(extension);
  }
}
