package com.nniett.kikaishin.web.controller.web;

import com.nniett.kikaishin.web.service.ShelfService;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelves")
public class ShelfController {

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
