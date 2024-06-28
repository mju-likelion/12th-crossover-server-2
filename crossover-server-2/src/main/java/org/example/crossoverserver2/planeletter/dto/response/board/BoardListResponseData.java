package org.example.crossoverserver2.planeletter.dto.response.board;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.dto.response.PaginationDto;
import org.example.crossoverserver2.planeletter.model.Board;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BoardListResponseData {
    private List<BoardDto> boardList;
    private PaginationDto pagination;

    public static BoardListResponseData boardListResponseData(Page<Board> page){
        return BoardListResponseData.builder()
                .boardList(page.stream()
                        //BoardDto 형식으로 변환
                        .map(board -> BoardDto.boardDto(board))
                        .collect(Collectors.toList()))
                .pagination(PaginationDto.paginationDto(page))
                .build();
    }
}
