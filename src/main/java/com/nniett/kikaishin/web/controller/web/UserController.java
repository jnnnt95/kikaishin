package com.nniett.kikaishin.web.controller.web;

import com.nniett.kikaishin.web.service.UserService;
import com.nniett.kikaishin.web.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
