package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.write.LoginDto;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserController userController;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserController userController,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userController = userController;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto dto) {
        dto.setUsername(dto.getUsername().trim());
        if(userController.exists(dto.getUsername())) {
            if(!userController.isBlocked(dto.getUsername())) {
                UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                );
                try {
                    authenticationManager.authenticate(login);
                    this.authenticationManager.authenticate(login);
                    String jwt = jwtUtils.create(dto.getUsername());
                    userController.reportSuccessfulAuthentication(dto.getUsername());
                    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
                } catch(AuthenticationException e) {
                    userController.reportFailedAuthentication(dto.getUsername());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
