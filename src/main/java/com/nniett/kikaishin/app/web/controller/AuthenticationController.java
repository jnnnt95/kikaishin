package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.write.LoginDto;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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

        logger.info("AuthenticationController initialized.");
    }

    @PostMapping("/login")
    @Operation(description = "Allows login to Kikaishin app. Provides as successful response a JWT as Authentication header.")
    @ApiResponses({
            @ApiResponse(description = "Successfully logged in; JWT generated.", responseCode = "200"),
            @ApiResponse(description = "User is either locked or disabled.", responseCode = "418"),
            @ApiResponse(description = "Provided user does not exist.", responseCode = "404"),
            @ApiResponse(description = "Authentication process failed.", responseCode = "403")
    })
    @Parameters({
            @Parameter(name = "LoginDto", schema = @Schema(implementation = LoginDto.class))
    })
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto dto) {
        dto.setUsername(dto.getUsername().trim());
        logger.debug("Authentication requested using method: {}.", "login(LoginDto)");
        logger.trace("Authentication request body: {}.", dto);
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
                    logger.debug("Login succeeded. Returning positive response.");
                    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
                } catch(AuthenticationException e) {
                    userController.reportFailedAuthentication(dto.getUsername());
                    logger.debug("Login failed. Report was performed. Returning forbidden response.");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } else {
                logger.debug("User is blocked. Meaning either locked or disabled. Returning negative login response.");
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
            }
        } else {
            logger.debug("User not found. Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

}
