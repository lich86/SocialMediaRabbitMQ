package com.chervonnaya.subscriptionsservice.controller;

import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.subscriptionsservice.service.UserService;
import com.chervonnaya.subscriptionsservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public User addUser(@RequestBody UserDTO dto) {
        return service.save(dto);
    }

    @PostMapping("/{id}")
    public User updateUser(@RequestBody UserDTO dto, @PathVariable Long id) {
        return service.update(dto, id);
    }
}

