package org.example.crossoverserver2.planeletter.dto.response.clause;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.model.Clause;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ClauseListResponseData {

    private List<ClauseDto> clauseDtos;

    public static ClauseListResponseData clauseListResponseData(List<Clause> clauses) {
        return new ClauseListResponseData(clauses.stream()
                .map(i -> ClauseDto.clauseDto(i))
                .collect(Collectors.toList()));
    }
}
