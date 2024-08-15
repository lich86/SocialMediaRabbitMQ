package com.chervonnaya.subscriptionsservice.controller;

import com.chervonnaya.subscriptionsservice.model.User;
import com.chervonnaya.subscriptionsservice.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    SubscriptionService service;

    @PostMapping("/{id}/follow")
    public User follow(@PathVariable Long id) {
        Long stubbedUserId = 3L;
        return service.follow(stubbedUserId, id);
    }

    @PostMapping("/{id}/unfollow")
    public User unfollow(@PathVariable Long id) {
        Long stubbedUserId = 1L;
        return service.unfollow(stubbedUserId, id);
    }
}
