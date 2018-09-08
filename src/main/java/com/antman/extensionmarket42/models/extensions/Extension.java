package com.antman.extensionmarket42.models.extensions;

import com.antman.extensionmarket42.models.UserProfile;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

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
  private java.sql.Date lastCommit;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          name = "extension_tags",
          joinColumns = @JoinColumn(name = "extensionId"),
          inverseJoinColumns = @JoinColumn(name = "tagId")
  )
  private Set<Tag> tags;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "ownerId")
  private UserProfile userProfile;

  @OneToMany(mappedBy = "extension")
  private List<Screenshot> screenshots;

  @Column
  private boolean pending;

  @Column
  private boolean featured;

  @Column
  private String icon;

  @Column
  private Date addedOn;

  @Column
  private boolean active;

  public Extension() {
  }

  public Extension(String name, String description, String version, int downloadsCount, String downloadLink, String repoLink, int openIssues, int pullRequests, Date lastCommit, UserProfile userProfile, List<Screenshot> screenshots, boolean pending, boolean featured, String icon, Date addedOn) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.downloadsCount = downloadsCount;
    this.downloadLink = downloadLink;
    this.repoLink = repoLink;
    this.openIssues = openIssues;
    this.pullRequests = pullRequests;
    this.lastCommit = lastCommit;
    this.userProfile = userProfile;
    this.screenshots = screenshots;
    this.pending = pending;
    this.featured = featured;
    this.icon = icon;
    this.addedOn = addedOn;
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

  public void setPullRequests(int pullRequests) {
    this.pullRequests = pullRequests;
  }

  public Date getLastCommit() {
    return lastCommit;
  }

  public void setLastCommit(Date lastCommit) {
    this.lastCommit = lastCommit;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
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

  public boolean isPending() {
    return pending;
  }

  public void setPending(boolean pending) {
    this.pending = pending;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Date getAddedOn() {
    return addedOn;
  }

  public void setAddedOn(Date addedOn) {
    this.addedOn = addedOn;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
