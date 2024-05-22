package com.nniett.kikaishin.app.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nniett.kikaishin.app.service.BeanUtil;
import com.nniett.kikaishin.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // this value is recommended to be updated periodically. Maybe this periodical update can be implemented in future api update
    private static final String SECRET_KEY = "jIeqAFGetwkqcEm6xfKlkW0jUxwXimK98LofVMVo492GPcNVTEsNfenl1BesCARm";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public JwtUtils() {
        logger.info("JwtUtils initialized.");
    }

    public String create(String username) {
        logger.trace("Creating JWT for username {}.", username);
        return JWT
                .create()
                .withSubject(username)
                .withIssuer("kikaishin-api")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .sign(ALGORITHM);
    }

    public boolean isValidWithUsernameCheck(String token) {
        String username = getUsername(token);
        logger.debug("JWT validation.");
        if(((UserService) BeanUtil.getBean("userService")).checkIsBlocked(username)) {
            logger.debug("JWT not valid because user is blocked");
            logger.trace("JWT not valid because user is blocked. Username: {}.", username);
            return false;
        }
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(token);
        }
        catch(JWTVerificationException e) {
            logger.debug("JWT determined not to be valid. Reason: {}.", e.getMessage());
            return false;
        }
        logger.debug("JWT determined valid.");
        return true;
    }

    public boolean isValid(String token) {
        logger.debug("JWT validation.");
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(token);
        }
        catch(JWTVerificationException e) {
            logger.debug("JWT determined not to be valid. Reason: {}.", e.getMessage());
            return false;
        }
        logger.debug("JWT determined valid.");
        return true;
    }

    public String getUsername(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();
    }

}
