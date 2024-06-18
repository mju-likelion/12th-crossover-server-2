package org.example.crossoverserver2.planeletter.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class WriteBoardDto {
    @NotBlank(message = "게시글의 제목이 누락되었습니다.")
    @Size(max = 20)
    private String title;
    @NotBlank(message = "게시글의 내용이 누락되었습니다.")
    @Size(max = 140)
    private String content;
}
