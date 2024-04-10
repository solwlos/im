package com.sol.admin.modules.system.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRole implements Authentication {

    List<GrantedAuthority> authorities = new ArrayList<>();
    String username;
    String password;
    String role_id;
    boolean isAuthenticated = false;

    String id;

    public UserRole() {

    }


    public UserRole(String name, String password) {
        this.username = name;
        this.password = password;
    }
    public String getRole() {
        return role_id;
    }

    public void setRole(String role) {
        this.role_id = role;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }
    /**
     * 返回权限，这个权限是前缀"ROLE_"+role
     * @param role
     */
    public void setAuthority(String role) {
        GrantedAuthority auth = () -> {
            return "ROLE_" + role;
        };
        authorities.add(auth);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "authorities=" + authorities +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role_id + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                ", id=" + id +
                '}';
    }
}
