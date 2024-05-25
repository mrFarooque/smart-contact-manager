package com.smartcontactmanager.scm.model;

import com.smartcontactmanager.scm.model.enums.Provider;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@ToString
public class User implements UserDetails {
    private String id;
    private String name;
    private String email;
    private String password;
    private String about;
    private String profilePic;
    private String phoneNumber;
    private Provider provider;
    private boolean userEnabled = true;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userEnabled;
    }
}
