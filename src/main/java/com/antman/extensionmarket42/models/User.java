package com.antman.extensionmarket42.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column
  private String username;
  @Column
  private String password;
  @Column
  private int enabled;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "username")
  private UserProfile userProfile;

  public User(){
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

}
