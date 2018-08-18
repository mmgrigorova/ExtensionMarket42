package com.antman.extensionmarket42.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "extensions")
public class Extension {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private String version;

  @Column
  private int downloadsCount;

  @Column
  private String downloadLink;

  @Column
  private String repoLink;

  @Column
  private int openIssues;

  @Column
  private int pullReq;

  @Column
  private Date lastCommit;

  @OneToMany(mappedBy = "extension")
  List<ExtensionTag> extensionTags;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "ownerId")
  private UserProfile userProfile;

  @OneToMany(mappedBy = "extension")
  private List<Screenshot> screenshots;

  @Column
  private boolean pending;

  @Column
  private String icon;

  public Extension() {
  }

  public Extension(String name, String description, String version, int downloadsCount, String downloadLink, String repoLink, int openIssues, int pullReq, Date lastCommit) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.downloadsCount = downloadsCount;
    this.downloadLink = downloadLink;
    this.repoLink = repoLink;
    this.openIssues = openIssues;
    this.pullReq = pullReq;
    this.lastCommit = lastCommit;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public int getDownloadsCount() {
    return downloadsCount;
  }

  public void setDownloadsCount(int downloadsCount) {
    this.downloadsCount = downloadsCount;
  }

  public String getDownloadLink() {
    return downloadLink;
  }

  public void setDownloadLink(String downloadLink) {
    this.downloadLink = downloadLink;
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

  public int getPullReq() {
    return pullReq;
  }

  public void setPullReq(int pullReq) {
    this.pullReq = pullReq;
  }

  public Date getLastCommit() {
    return lastCommit;
  }

  public void setLastCommit(Date lastCommit) {
    this.lastCommit = lastCommit;
  }


  public boolean isPending() {
    return pending;
  }

  public void setPending(boolean pending) {
    this.pending = pending;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public List<ExtensionTag> getExtensionTags() {
    return extensionTags;
  }

  public void setExtensionTags(List<ExtensionTag> extensionTags) {
    this.extensionTags = extensionTags;
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  public List<Screenshot> getScreenshots() {
    return screenshots;
  }

  public void setScreenshots(List<Screenshot> screenshots) {
    this.screenshots = screenshots;
  }
}
