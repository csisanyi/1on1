package com.getbridge.homework.rest.controller;

import com.getbridge.homework.rest.dto.UserDto;
import com.getbridge.homework.rest.entity.User;
import com.getbridge.homework.rest.repository.UserRepository;
import com.getbridge.homework.rest.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Util util;

    public UserController(UserRepository userRepository, Util util) {
        this.userRepository = userRepository;
        this.util = util;
    }

    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody UserDto user) {
        User newUser = userRepository.save(util.dtoToUsr(user));
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/getallusers")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getuserbyid/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateuser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteuser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            userRepository.deleteById(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
