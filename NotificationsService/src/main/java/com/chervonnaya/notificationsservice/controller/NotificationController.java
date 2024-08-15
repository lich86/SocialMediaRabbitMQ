package com.chervonnaya.notificationsservice.controller;

import com.chervonnaya.notificationsservice.model.Notification;
import com.chervonnaya.notificationsservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    @GetMapping("{id}")
    public Set<Notification> getAllBySubscriberId(@PathVariable Long id) {
        return service.getAllById(id);
    }

}
