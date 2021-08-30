package com.company.market.config;

import com.company.market.entity.ProfileEntity;
import com.company.market.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private ProfileRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = repository.findByEmail(s);
        optional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        ProfileEntity profile = optional.get();
        return new CustomUserDetails(profile);
    }
}
