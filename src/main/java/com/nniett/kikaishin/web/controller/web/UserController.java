package com.nniett.kikaishin.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //TODO: service impl

    @PostMapping("/password/update")
    public ResponseEntity<Void> changePassword() {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping("/name/update")
    public ResponseEntity<Void> changeDisplayName() {
        //TODO
        return ResponseEntity.ok().build();
    }

}
