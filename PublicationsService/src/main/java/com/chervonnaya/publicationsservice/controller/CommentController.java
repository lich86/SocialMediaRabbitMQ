package com.chervonnaya.publicationsservice.controller;

import com.chervonnaya.publicationsservice.dto.CommentDTO;
import com.chervonnaya.publicationsservice.model.Comment;
import com.chervonnaya.publicationsservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService service;

    @PostMapping
    public Comment addComment(@RequestBody CommentDTO dto) {
        return service.save(dto);
    }

    @PostMapping("/{id}")
    public Comment updateComment(@RequestBody CommentDTO dto, @PathVariable Long id) {
        return service.update(dto, id);
    }
}
