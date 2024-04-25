package com.nniett.kikaishin.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    //TODO: service impl

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> get(@PathVariable("id") int entityId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int entityId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PutMapping("/toggle_status")
    public ResponseEntity<Void> toggleActive(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping("/answers")
    public ResponseEntity<Void> addAnswer(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PutMapping("/answers")
    public ResponseEntity<Void> updateAnswer(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("id") int entityId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clues")
    public ResponseEntity<Void> addClue(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PutMapping("/clues")
    public ResponseEntity<Void> updateClue(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clues/{id}")
    public ResponseEntity<Void> deleteClue(@PathVariable("id") int entityId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping("/review")
    public ResponseEntity<Void> addReview(@RequestBody String entityDto) {
        //TODO
        return ResponseEntity.ok().build();
    }


}
