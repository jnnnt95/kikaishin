package com.nniett.kikaishin.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

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

}
