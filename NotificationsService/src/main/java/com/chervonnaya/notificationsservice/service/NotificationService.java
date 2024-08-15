package com.chervonnaya.notificationsservice.service;

import com.chervonnaya.dto.NotificationDTO;
import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.notificationsservice.config.RabbitMQConfig;
import com.chervonnaya.notificationsservice.model.Notification;
import com.chervonnaya.notificationsservice.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository repository;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATIONS_QUEUE)
    private void listenToNotifications(NotificationDTO dto) {
        Set<UserDTO> subscribers = dto.getSubscribers();
        String publisherFullName = dto.getPublisherFullName();
        for (UserDTO userDTO: subscribers) {
            String message = createMessage(userDTO, publisherFullName);
            sentEmail(userDTO.getEmail(), message);
            save(userDTO.getId(), message);
        }
    }

    private String createMessage(UserDTO dto, String publisherFullName) {
        return String.format("Hey, %s! %s has a new post, you might want to read it!",
            dto.getFullName(), publisherFullName);
    }

    private void sentEmail(String email, String message) {
        log.info("Sent message: {} to email: {}", message, email);
    }

    private Notification save(Long id, String message) {
        try {
            Notification notification = new Notification();
            notification.setSubscriberId(id);
            notification.setMessage(message);
            repository.save(notification);
            log.info("Saved new notification to {}", id);
            return notification;
        } catch (Exception e) {
            log.info("Unable to save new notification to {}", id);
            throw new RuntimeException("Unable to save new notification");
        }

    }

    public Set<Notification> getAllById(Long id) {
        return repository.findAllBySubscriberId(id);
    }

}
