package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.Board;
import org.example.crossoverserver2.planeletter.model.Comment;
import org.example.crossoverserver2.planeletter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findAllByBoard(Board board, Pageable pageable);
    boolean existsByUserAndId(User user, UUID id);
}
