package com.nniett.kikaishin.app.web.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public interface CanRetrieveUsernameFromJWT {
    default String getUsernameFromJWT(HttpServletRequest request, JwtUtils jwtUtils) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        String jwt = header.split(" ")[1].trim();
        if(!jwtUtils.isValid(jwt)) {
            return null;
        }

        return jwtUtils.getUsername(jwt);
    }
}
