package com.chervonnaya.publicationsservice.service;

import com.chervonnaya.dto.LikeDTO;
import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.publicationsservice.config.RabbitMQConfig;
import com.chervonnaya.publicationsservice.dto.PublicationDTO;
import com.chervonnaya.publicationsservice.model.Publication;
import com.chervonnaya.publicationsservice.repository.PublicationRepository;
import com.chervonnaya.publicationsservice.service.mappers.PublicationMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PublicationService {

    private final PublicationRepository repository;
    private final PublicationMapper mapper;
    private final RabbitTemplate jsonTemplate;
    private final RabbitTemplate stringTemplate;
    private final UserService userService;

    public PublicationService(PublicationRepository repository, PublicationMapper mapper,
                              @Qualifier("jsonRabbitTemplate")RabbitTemplate jsonTemplate,
                              @Qualifier("stringRabbitTemplate") RabbitTemplate stringTemplate, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.jsonTemplate = jsonTemplate;
        this.stringTemplate = stringTemplate;
        this.userService = userService;
    }

    public Publication getByID(Long id) {
        return repository.findById(id).
            orElseThrow(() -> new EntityNotFoundException(String.format("Unable to find publication, id: %d", id)));
    }

    @Transactional
    public Publication save(PublicationDTO dto) {
        try {
            Publication publication = repository.save(mapper.map(dto));
            log.info("Saved publication, id: {}", publication.getId());
            Long publisherId = dto.getUserId();
            stringTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.PUBLICATIONS_KEY, publisherId);
            return publication;
        } catch (Exception ex) {
            log.error("Unable to save new publication");
            throw new RuntimeException("Unable to save new publication", ex);
        }
    }

    @Transactional
    public Publication update(PublicationDTO dto, Long id) {
        repository.findById(id).
            orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        try {
            Publication publication = repository.save(mapper.map(dto));
            log.info("Updated publication, id: {}", publication.getId());
            return publication;
        } catch (Exception ex) {
            log.error("Unable to update publication, id: {}", id);
            throw new RuntimeException(String.format("Unable to update publication, id: %d", id), ex);
        }
    }

    @Transactional
    public Publication addLike(Long publicationId, Long subscriberId) {
        Publication publication = repository.findById(publicationId).
            orElseThrow(() -> new EntityNotFoundException(String.valueOf(publicationId)));
        try {
            List<Long> likes = publication.getLikes();
            likes.add(subscriberId);
            publication.setLikes(likes);
            repository.save(publication);
            UserDTO publisherDTO = userService.getUserDTO(publication.getUserId());
            UserDTO subscriberDTO = userService.getUserDTO(subscriberId);
            LikeDTO likeDTO = new LikeDTO();
            likeDTO.setPublisherFullName(publisherDTO.getFullName());
            likeDTO.setPublicationText(publication.getText());
            likeDTO.setPublisherEmail(publisherDTO.getEmail());
            likeDTO.setSubscriberFullName(subscriberDTO.getFullName());
            jsonTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.LIKES_KEY, likeDTO);
            return publication;
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Unable to add like to publication, id: %d, userId: %d", publicationId, publication.getUserId()), ex);
        }
    }

    @Transactional
    public Publication removeLike(Long publicationId, Long subscriberId) {
        Publication publication = repository.findById(publicationId).
            orElseThrow(() -> new EntityNotFoundException(String.valueOf(publicationId)));
        try {
            List<Long> likes = publication.getLikes();
            likes.remove(subscriberId);
            publication.setLikes(likes);
            repository.save(publication);
            return publication;
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Unable to remove like from publication, id: %d, userId: %d", publicationId, publication.getUserId()), ex);
        }
    }



}
