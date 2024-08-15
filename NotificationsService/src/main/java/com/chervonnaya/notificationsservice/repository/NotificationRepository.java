package com.chervonnaya.notificationsservice.repository;

import com.chervonnaya.notificationsservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.subscriberId = :id")
    Set<Notification> findAllBySubscriberId(Long id);
}
