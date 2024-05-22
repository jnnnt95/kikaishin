package com.nniett.kikaishin.app.web.security;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public interface CanRetrieveUsernameFromJWT {
    Logger logger = LoggerFactory.getLogger(CanRetrieveUsernameFromJWT.class);
    default String getUsernameFromJWT(HttpServletRequest request, JwtUtils jwtUtils) {
        logger.debug("Retrieving username from authorization header.");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")) {
            logger.debug("Authorization header malformed. Username cannot be retrieved.");
            return null;
        }

        String jwt = header.split(" ")[1].trim();
        if(!jwtUtils.isValid(jwt)) {
            logger.debug("JWT not valid. Username cannot be retrieved.");
            return null;
        }

        logger.debug("Returning expected positive response.");
        return jwtUtils.getUsername(jwt);
    }
}
