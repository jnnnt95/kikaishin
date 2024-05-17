package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.persistence.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.repository.findById(username).orElseThrow(
                () -> new UsernameNotFoundException("User '" + username + "' not found")
        );

        String[] roles = user.getRoles().stream().map(UserRoleEntity::getRole).distinct().toArray(String[]::new);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities(roles))
                .accountLocked(user.getLocked())
                .disabled(user.getDisabled())
                .build();
    }

//    private String[] getAuthorities(String role) {
//        if("ADMIN".equals(role) || "CUSTOMER".equals(role)) {
//            return new String[]{"random_order"};
//        }
//        else{
//            return new String[]{};
//        }
//    }

    private List<GrantedAuthority> grantedAuthorities(String[] roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//            for(String authority: this.getAuthorities(role)) {
//                authorities.add(new SimpleGrantedAuthority(authority));
//            }
        }

        return authorities;
    }
}
