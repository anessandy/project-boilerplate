package com.mepro.appname.provider;

import com.mepro.appname.dto.UserInfoDto;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private UserInfoDto user;
    
    public CustomUserDetails(UserInfoDto user) {
        this.user = user;
    }
    
    public UserInfoDto getUserInfo() {
        return user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }
    
    @Override
    public String getUsername() {
        return user.getUserId();
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
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
        return true;
    }
}
