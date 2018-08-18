package com.antman.extensionmarket42.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id()
  private String username;
  @Column
  private String password;
  @Column
  private int enabled;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "username" ,insertable = false, updatable = false)
  private UserRole userRole;

  @OneToOne(mappedBy = "user")
  private UserProfile userProfile;

  public User(){
  }

  public User(String username, String password, UserRole userRole, UserProfile userProfile) {
    this.username = username;
    this.password = password;
    this.userRole = userRole;
    this.userProfile = userProfile;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getEnabled() {
    return enabled;
  }

  public void setEnabled(int enabled) {
    this.enabled = enabled;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }
}
