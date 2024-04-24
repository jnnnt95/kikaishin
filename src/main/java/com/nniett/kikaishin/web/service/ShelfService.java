package com.nniett.kikaishin.web.service;

import com.nniett.kikaishin.web.persistence.repository.ShelfRepository;
import com.nniett.kikaishin.web.service.mapper.ShelfMapper;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShelfService {

    private final ShelfRepository repository;
    private final ShelfMapper mapper;

    @Autowired
    public ShelfService(ShelfRepository repository, ShelfMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Shelf> findAll() {
        return this.mapper.toPojos(this.repository.findAll());
    }
}
