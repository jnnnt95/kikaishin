package com.nniett.kikaishin.app.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    //TODO: service impl


    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        //TODO: impl after implementing JWT
        return ResponseEntity.ok().build();
    }

}
