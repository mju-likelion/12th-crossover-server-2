package org.example.crossoverserver2.planeletter.dto.response.board;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponseData {
    private List<BoardDto> boardList;
    private PaginationDto pagination;
}