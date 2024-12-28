package com.main.apijava.controller;


import com.main.apijava.entity.User;
import com.main.apijava.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var id = userService.createUser(createUserDto);

        return ResponseEntity.created(URI.create("/users" + id.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") UUID userId) {
        var user = userService.getUserById(userId);
    if (user == null) {
        return ResponseEntity.notFound().build();
    } else {
        return ResponseEntity.ok(user);
    }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") UUID userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") UUID userId,
                                               @RequestBody UpdateUserDto updateUserDto) {
        userService.updateById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }
}
