package com.chervonnaya.publicationsservice.controller;

import com.chervonnaya.publicationsservice.config.RabbitMQConfig;
import com.chervonnaya.publicationsservice.dto.PublicationDTO;
import com.chervonnaya.publicationsservice.model.Publication;
import com.chervonnaya.publicationsservice.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publication")
public class PublicationController {

    private final PublicationService service;

    public PublicationController(PublicationService service) {
        this.service = service;
    }

    @PostMapping
    public Publication addPublication(@RequestBody PublicationDTO dto) {
        return service.save(dto);
    }

    @PostMapping("/{id}")
    public Publication updatePublication(@RequestBody PublicationDTO dto, @PathVariable Long id) {
        return service.update(dto, id);
    }

    @PostMapping("/{id}/like")
    public Publication addLike(@PathVariable Long id) {
        Long stabbedUserId = 1L;
        return service.addLike(id, stabbedUserId);
    }

    @PostMapping("/{id}/removeLike")
    public Publication removeLike(@PathVariable Long id) {
        Long stabbedUserId = 1L;
        return service.removeLike(id, stabbedUserId);
    }
}
