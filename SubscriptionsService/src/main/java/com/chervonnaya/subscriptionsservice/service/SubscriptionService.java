package com.chervonnaya.subscriptionsservice.service;

import com.chervonnaya.dto.NotificationDTO;
import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.subscriptionsservice.config.RabbitMQConfig;
import com.chervonnaya.subscriptionsservice.model.User;
import com.chervonnaya.subscriptionsservice.repository.UserRepository;
import com.chervonnaya.subscriptionsservice.service.mappers.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final RabbitTemplate template;

    public User follow(Long subscriberId, Long publisherId) {
        User subscriber = repository.findById(subscriberId)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(subscriberId)));
        User publisher = repository.findById(publisherId)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(publisherId)));
        try {
            List<User> publishers = subscriber.getSubscriptions();
            publishers.add(publisher);
            subscriber.setSubscriptions(publishers);
            repository.save(subscriber);
            log.info("Add new publisher id: {} to subscriber {}", publisherId, subscriberId);
            return subscriber;
        } catch (Exception ex) {
            log.info("Unable to add new publisher id: {} to subscriber {}", publisherId, subscriberId);
            throw new RuntimeException(
                String.format("Unable to add new publisher id: %d to subscriber %d", publisherId, subscriberId));
        }
    }

    public User unfollow(Long subscriberId, Long publisherId) {
        User subscriber = repository.findById(subscriberId)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(subscriberId)));
        User publisher = repository.findById(publisherId)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(publisherId)));
        try {
            List<User> publishers = subscriber.getSubscriptions();
            publishers.remove(publisher);
            subscriber.setSubscriptions(publishers);
            repository.save(subscriber);
            log.info("Removed publisher id: {} from subscriber {}", publisherId, subscriberId);
            return subscriber;
        } catch (Exception ex) {
            log.info("Unable to remove publisher id: {} from subscriber {}", publisherId, subscriberId);
            throw new RuntimeException(
                String.format("Unable to remove publisher id: %d from subscriber %d", publisherId, subscriberId));
        }
    }


    @RabbitListener(queues = RabbitMQConfig.PUBLICATIONS_QUEUE)
    private void listenToPublications(String publisherId) {
        User publisher = repository.findById(Long.valueOf(publisherId))
            .orElseThrow(() -> new EntityNotFoundException(publisherId));
        try {
            Set<UserDTO> subscribers = publisher.getSubscribers().stream().map(mapper::map).collect(Collectors.toSet());
            NotificationDTO dto = new NotificationDTO();
            dto.setPublisherFullName(publisher.getFullName());
            dto.setSubscribers(subscribers);
            template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.NOTIFICATIONS_KEY, dto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
