package org.example.crossoverserver2.planeletter.dto.response.comment;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.model.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CommentDto {
    private UUID id;
    private String name;
    private String content;
    private LocalDateTime createdTime;

    public static CommentDto commentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getUser().getName())
                .content(comment.getContent())
                .createdTime(comment.getCreatedAt())
                .build();
    }
}
