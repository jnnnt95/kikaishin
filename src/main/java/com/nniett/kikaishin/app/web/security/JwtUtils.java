package com.nniett.kikaishin.app.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nniett.kikaishin.app.service.BeanUtil;
import com.nniett.kikaishin.app.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    // this value is recommended to be updated periodically. Maybe this periodical update can be implemented in future api update
    private static final String SECRET_KEY = "jIeqAFGetwkqcEm6xfKlkW0jUxwXimK98LofVMVo492GPcNVTEsNfenl1BesCARm";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String create(String username) {
        return JWT
                .create()
                .withSubject(username)
                .withIssuer("Kikaishin")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .sign(ALGORITHM);
    }

    public boolean isValid(String token) {
        if(((UserService) BeanUtil.getBean("userService")).checkIsBlocked(getUsername(token))) {
            return false;
        }
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(token);
        }
        catch(JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public String getUsername(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();
    }

}
