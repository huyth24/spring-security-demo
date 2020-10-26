package com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetail implements UserDetails {

    private User user;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = user.getRoles() != null ? user.getRoles() : new ArrayList<>();
        return roles.stream().map(el -> new SimpleGrantedAuthority(el.getRoleName())).collect(Collectors.toList());
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public CustomUserDetail(User user) {
        this.user = user;
    }

    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
