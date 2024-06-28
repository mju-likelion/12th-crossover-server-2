package org.example.crossoverserver2.planeletter.dto.response.board;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.model.Board;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDto {
    private String name;
    private String title;
    private String content;
    private LocalDateTime createdTime;

    public static BoardDto boardDto(Board board){
        return BoardDto.builder()
                .name(board.getUser().getName())
                .title(board.getTitle())
                .content(board.getContent())
                .createdTime(board.getCreatedAt())
                .build();
    }
}
