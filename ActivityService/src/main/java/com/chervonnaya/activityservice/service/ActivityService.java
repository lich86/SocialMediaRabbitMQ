package com.chervonnaya.activityservice.service;

import com.chervonnaya.activityservice.config.RabbitMQConfig;
import com.chervonnaya.dto.LikeDTO;
import com.chervonnaya.dto.NewCommentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityService {

    @RabbitListener(queues = RabbitMQConfig.LIKES_QUEUE)
    private void listenToLikes(LikeDTO dto) {
        String message = createLikeMessage(dto);
        sentEmail(dto.getPublisherEmail(), message);
    }

    @RabbitListener(queues = RabbitMQConfig.COMMENTS_QUEUE)
    private void listenToComments(NewCommentDTO dto) {
        String message = createCommentMessage(dto);
        sentEmail(dto.getPublisherEmail(), message);
    }


    private String createLikeMessage(LikeDTO dto) {
        return String.format("Hey, %s! %s liked your post: %s",
            dto.getPublisherFullName(), dto.getSubscriberFullName(), dto.getPublicationText());
    }

    private String createCommentMessage(NewCommentDTO dto) {
        return String.format("Hey, %s! %s left a comment: %s to your post: %s",
            dto.getPublisherFullName(), dto.getSubscriberFullName(), dto.getCommentText(), dto.getPublicationText());
    }

    private void sentEmail(String email, String message) {
        log.info("Sent message: {} to email: {}", message, email);
    }
}
