package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}
