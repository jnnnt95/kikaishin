package com.nniett.kikaishin.web.service;

import com.nniett.kikaishin.web.persistence.repository.UserRepository;
import com.nniett.kikaishin.web.service.mapper.UserMapper;
import com.nniett.kikaishin.web.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<User> getAll() {
        return this.mapper.toPojos(this.repository.findAll());
    }
}
