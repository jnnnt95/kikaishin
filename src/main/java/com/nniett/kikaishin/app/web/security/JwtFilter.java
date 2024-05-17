package com.nniett.kikaishin.app.web.security;

import com.nniett.kikaishin.app.service.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter implements CanRetrieveUsernameFromJWT {

    private final JwtUtils jwtUtils;
    private final UserSecurityService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtils jwtUtils, UserSecurityService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. Validate Authorization header.
        // 2. Validate JWT.
        // 3. Load user from UserDetailsService.
        // 4. Load user to security context.

        //1. 2.
        String username = getUsernameFromJWT(request, this.jwtUtils);
        if(username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //3.
        User userDetails = (User) this.userDetailsService.loadUserByUsername(username);

        //4.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
