package org.example.crossoverserver2.planeletter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class WriteCommentDto {
    @NotBlank(message = "댓글의 내용이 누락되었습니다.")
    @Size(max = 100)
    private String content;
}