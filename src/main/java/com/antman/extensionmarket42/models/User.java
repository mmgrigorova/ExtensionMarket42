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

  public User(){

  }

  public User(String username, String password, int enabled) {
    this.username = username;
    this.password = password;
    this.enabled = enabled;
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
