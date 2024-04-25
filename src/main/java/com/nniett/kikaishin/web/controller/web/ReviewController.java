package com.nniett.kikaishin.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    //TODO: service impl

    @GetMapping("/sets/{scope}")
    public ResponseEntity<Void> getReviewList(@PathVariable String scope) {
        //TODO
        return ResponseEntity.ok().build();
    }

}
