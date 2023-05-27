package com.getbridge.homework.rest.controller;


import com.getbridge.homework.rest.dto.UserDto;
import com.getbridge.homework.rest.entity.User;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.repository.UserRepository;
import com.getbridge.homework.rest.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class Controller {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Util util;

    public Controller(OneOnOneRepository oneOnOneRepository, UserRepository userRepository, Util util) {
        this.oneOnOneRepository = oneOnOneRepository;
        this.userRepository = userRepository;
        this.util = util;
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserDto user) {
        return ResponseEntity.ok(userRepository.save(util.dtoToUsr(user)));
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
