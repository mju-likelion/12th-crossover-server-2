package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.UserClause;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserClauseRepository extends JpaRepository<UserClause, UUID> {
}
