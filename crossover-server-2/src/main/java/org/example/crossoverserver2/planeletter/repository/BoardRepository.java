package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.Board;
import org.example.crossoverserver2.planeletter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    boolean existsByUserAndId(User user, UUID id);
    Board findBoardById(UUID id);
    Page<Board> findAll(Pageable pageable);
}
