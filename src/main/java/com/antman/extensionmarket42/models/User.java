package com.antman.extensionmarket42.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id()
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String username;

    @Column
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @Column
    private int enabled;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private List<UserRole> userRoles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private UserProfile userProfile;

    public User() {
    }

    public User(String username, String password, UserProfile userProfile) {
        this.username = username;
        this.password = password;
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

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRole) {
        this.userRoles = userRole;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
