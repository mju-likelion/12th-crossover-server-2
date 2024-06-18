package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.Board;
import org.example.crossoverserver2.planeletter.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByBoard(Board board);
}
