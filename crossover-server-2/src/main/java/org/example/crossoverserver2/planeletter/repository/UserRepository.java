package org.example.crossoverserver2.planeletter.repository;

import org.example.crossoverserver2.planeletter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
