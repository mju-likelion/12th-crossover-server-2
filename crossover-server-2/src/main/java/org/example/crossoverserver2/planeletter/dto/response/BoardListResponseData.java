package org.example.crossoverserver2.planeletter.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.model.Board;

import java.util.List;

@Getter
@Builder
public class BoardListResponseData {
    private List<BoardDto> boardList;
    private List<Board> pagenation; //페이지 정보
}
