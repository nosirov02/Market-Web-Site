package com.company.market.config;

import com.company.market.entity.ProfileEntity;
import com.company.market.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private int id;
    private String userName;
    private String password;
    private boolean enabled;

    private List<GrantedAuthority> authorityList;

    public CustomUserDetails(ProfileEntity profile) {
        id = profile.getId();
        userName = profile.getEmail();
        password = profile.getPassword();
        enabled = profile.getStatus().equals(ProfileStatus.ACTIVE);

        this.authorityList = Arrays.asList(new SimpleGrantedAuthority(profile.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return enabled;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", authorityList=" + authorityList +
                '}';
    }
}
