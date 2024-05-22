package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.persistence.entity.UserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityService.class);

    private final UserRepository repository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.repository = userRepository;
        logger.info("UserSecurityService initialized.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user.");
        logger.trace("Loading user by username {}.", username);
        UserEntity user = this.repository.findById(username).orElseThrow(
                () -> new UsernameNotFoundException("User '" + username + "' not found")
        );

        logger.debug("User loaded. Preparing roles.");
        logger.trace("User roles {}.", Collections.singletonList(user.getRoles()));
        String[] roles = user.getRoles().stream().map(UserRoleEntity::getRole).distinct().toArray(String[]::new);


        logger.debug("Building UserDetails.");
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities(roles))
                .accountLocked(user.getLocked())
                .disabled(user.getDisabled())
                .build();
    }

    private List<GrantedAuthority> grantedAuthorities(String[] roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return authorities;
    }
}
