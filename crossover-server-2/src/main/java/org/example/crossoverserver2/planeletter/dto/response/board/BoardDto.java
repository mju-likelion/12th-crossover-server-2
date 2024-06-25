package org.example.crossoverserver2.planeletter.dto.response.board;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.model.Board;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BoardDto {
    private UUID id;
    private String name;
    private String title;
    private String content;
    private LocalDateTime createdTime;

    public static BoardDto boardDto(Board board){
        return BoardDto.builder()
                .id(board.getId())
                .name(board.getUser().getName())
                .title(board.getTitle())
                .content(board.getContent())
                .createdTime(board.getCreatedAt())
                .build();
    }
}
