package org.example.crossoverserver2.planeletter.service;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.model.Clause;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.model.UserClause;
import org.example.crossoverserver2.planeletter.repository.ClauseRepository;
import org.example.crossoverserver2.planeletter.repository.UserClauseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClauseService {
    private final UserClauseRepository userClauseRepository;
    private final ClauseRepository clauseRepository;

    public final void essentialRegister(User user){
        List<Clause> clauses = clauseRepository.findAll();

        clauses.stream().forEach(i->userClauseRepository.save(
                UserClause.builder()
                        .clause(i)
                        .user(user)
                .build()
        ));

    }


}
