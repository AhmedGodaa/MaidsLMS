package cc.maids.lms.config.userInstance;

import cc.maids.lms.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    String username;
    String password;
    Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}