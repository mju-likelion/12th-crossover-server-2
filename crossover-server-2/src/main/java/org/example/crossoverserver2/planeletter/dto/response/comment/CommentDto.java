package org.example.crossoverserver2.planeletter.dto.response.comment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {
    private String name;
    private String content;
    private LocalDateTime createdTime;
}
