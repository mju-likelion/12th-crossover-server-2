package org.example.crossoverserver2.planeletter.dto.response.clause;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.crossoverserver2.planeletter.model.Clause;


@Getter
@AllArgsConstructor
public class ClauseDto {

    public String title;

    public String content;

    public static ClauseDto clauseDto(Clause clause){
        return new ClauseDto(clause.getTitle(), clause.getContent());
    }

}
