package com.nniett.kikaishin.app.web.security;

import com.nniett.kikaishin.app.service.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtils jwtUtils;
    private final UserSecurityService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtils jwtUtils, UserSecurityService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        logger.info("JwtFilter initialized.");
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

        String username;

        logger.debug("Retrieving username from authorization header.");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")) {
            logger.debug("Authorization header malformed. Username cannot be retrieved.");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.split(" ")[1].trim();
        if(!jwtUtils.isValidWithUsernameCheck(jwt)) {
            logger.debug("JWT not valid. Username cannot be retrieved.");
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtUtils.getUsername(jwt);

        if(username == null) {
            logger.debug("Username could not be retrieved. Rejecting request.");
            filterChain.doFilter(request, response);
            return;
        }
        logger.trace("Username retrieved. Username is {}.", username);

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
        logger.debug("JWT Filter succeeded verification. Continuing filter chain.");
        filterChain.doFilter(request, response);
    }
}
