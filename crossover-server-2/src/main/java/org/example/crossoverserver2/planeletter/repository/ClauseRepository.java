package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.Clause;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClauseRepository extends JpaRepository<Clause, UUID> {
    Optional<List<Clause>> findAllByEssential(boolean essential);
}
