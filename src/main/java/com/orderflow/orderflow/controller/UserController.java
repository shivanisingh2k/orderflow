package com.orderflow.orderflow.controller;

import org.springframework.web.bind.annotation.RestController;

import com.orderflow.orderflow.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.orderflow.orderflow.dto.UserRequest;
import com.orderflow.orderflow.dto.UserResponse;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import com.orderflow.orderflow.dto.UserRequest;
import com.orderflow.orderflow.dto.UserResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/users")
    public UserResponse createUser(
            @RequestBody @Valid UserRequest userRequest) {

        return userService.createUser(userRequest);
    }
    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @PutMapping("/users/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserRequest userRequest) {

        return userService.updateUser(id, userRequest);
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}