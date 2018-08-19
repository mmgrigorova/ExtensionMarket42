package com.antman.extensionmarket42.extensions.models;

import com.antman.extensionmarket42.models.UserProfile;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "extensions")
public class Extension {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "extensionId")
  private Long id;

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
  private int pullRequests;

  @Column
  private Date lastCommit;

  @OneToMany(mappedBy = "extension")
  private List<ExtensionTag> extensionTags;

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

  public Extension(String name, String description, String version, int downloadsCount, String downloadLink, String repoLink, int openIssues, int pullRequests, Date lastCommit) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.downloadsCount = downloadsCount;
    this.downloadLink = downloadLink;
    this.repoLink = repoLink;
    this.openIssues = openIssues;
    this.pullRequests = pullRequests;
    this.lastCommit = lastCommit;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public int getPullRequests() {
    return pullRequests;
  }

  public void setPullRequests(int pullReq) {
    this.pullRequests = pullReq;
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
