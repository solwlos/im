package com.sol.admin.modules.system.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole implements Authentication {

    List<GrantedAuthority> authorities = new ArrayList<>();
    String username;
    String password;
    String role_id;
    boolean isAuthenticated = false;
    String id;

    public UserRole(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public String setId(String id) {
        return id;
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
//    public void setAuthority(String role) {
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//    }
    public void setAuthority(String role) {
        GrantedAuthority auth = () -> {
            return "ROLE_" + role;
        };
        authorities.add(auth);
    }
}
