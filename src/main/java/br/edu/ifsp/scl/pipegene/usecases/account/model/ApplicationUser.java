package br.edu.ifsp.scl.pipegene.usecases.account.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class ApplicationUser implements UserDetails {

    private UUID id;
    private String username;
    private String name;
    private String orcid;
    private String github;
    private String password;
    private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    public ApplicationUser(String username, String name, String orcid, String github, String password) {
        this.username = username;
        this.name = name;
        this.orcid = orcid;
        this.github = github;
        this.password = password;
        isAccountNonExpired = true;
        isAccountNonLocked = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
    }

    private ApplicationUser(UUID id, String username, String name, String orcid, String github, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.orcid = orcid;
        this.github = github;
        this.password = password;
        isAccountNonExpired = true;
        isAccountNonLocked = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
    }

    public ApplicationUser(UUID id, String username, String name, String orcid, String github, String password,
                           boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
                           boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.orcid = orcid;
        this.github = github;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrcid() {
        return orcid;
    }

    public String getGithub() {
        return github;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public ApplicationUser getNewInstanceWithId(UUID id) {
        return new ApplicationUser(id, username, name, orcid, github, password);
    }
}
