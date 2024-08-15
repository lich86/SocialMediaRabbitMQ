package com.chervonnaya.publicationsservice.service;

import com.chervonnaya.dto.NewCommentDTO;
import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.publicationsservice.config.RabbitMQConfig;
import com.chervonnaya.publicationsservice.dto.CommentDTO;
import com.chervonnaya.publicationsservice.model.Comment;
import com.chervonnaya.publicationsservice.model.Publication;
import com.chervonnaya.publicationsservice.repository.CommentRepository;
import com.chervonnaya.publicationsservice.service.mappers.CommentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserService userService;
    private final PublicationService publicationService;
    private final RabbitTemplate jsonTemplate;

    public CommentService(CommentRepository repository, CommentMapper mapper, UserService userService,
                          PublicationService publicationService,
                          @Qualifier("jsonRabbitTemplate")RabbitTemplate jsonTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
        this.publicationService = publicationService;
        this.jsonTemplate = jsonTemplate;
    }

    @Transactional
    public Comment save(CommentDTO dto) {
        try {
            Comment comment = repository.save(mapper.map(dto));
            Long subscriberId = dto.getUserId();
            Publication publication = publicationService.getByID(dto.getPublicationId());
            Long publisherId = publication.getUserId();
            UserDTO subscriber = userService.getUserDTO(subscriberId);
            UserDTO publisher = userService.getUserDTO(publisherId);
            NewCommentDTO commentDTO = new NewCommentDTO();
            commentDTO.setPublisherFullName(publisher.getFullName());
            commentDTO.setSubscriberFullName(subscriber.getFullName());
            commentDTO.setPublisherEmail(publisher.getEmail());
            commentDTO.setPublicationText(publication.getText());
            commentDTO.setCommentText(dto.getText());
            jsonTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.COMMENTS_KEY, commentDTO);
            log.info("Saved comment, id: {}, publicationId: {}", comment.getId(), comment.getPublication().getId());
            return comment;
        } catch (Exception ex) {
            log.error("Unable to save new comment");
            throw new RuntimeException("Unable to save new comment", ex);
        }
    }

    @Transactional
    public Comment update(CommentDTO dto, Long id) {
        repository.findById(id).
            orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        try {
            Comment comment = repository.save(mapper.map(dto));
            log.info("Updated comment, id: {}, publicationId: {}", comment.getId(), comment.getPublication().getId());
            return comment;
        } catch (Exception ex) {
            log.error("Unable to update comment, id: {}", id);
            throw new RuntimeException(String.format("Unable to update comment, id: %d", id), ex);
        }
    }
}
