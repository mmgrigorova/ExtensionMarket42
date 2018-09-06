package com.antman.extensionmarket42.models.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

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
    java.sql.Timestamp timestamp = new Timestamp(lastRefreshDate);
    this.lastRefreshDate = timestamp;
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

}
