package com.chervonnaya.publicationsservice.repository;

import com.chervonnaya.publicationsservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
}
