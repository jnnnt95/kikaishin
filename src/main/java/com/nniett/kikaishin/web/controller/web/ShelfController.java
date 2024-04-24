package com.nniett.kikaishin.web.controller.web;

import com.nniett.kikaishin.web.service.ShelfService;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class ShelfController {

    private final ShelfService service;

    @Autowired
    public ShelfController(ShelfService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Shelf>> getAllTrees() {
        List<Shelf> trees = this.service.findAll();
        return ResponseEntity.ok(trees);
    }
}
