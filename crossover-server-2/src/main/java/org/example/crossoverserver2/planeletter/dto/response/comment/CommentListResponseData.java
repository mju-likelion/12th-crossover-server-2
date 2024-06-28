package org.example.crossoverserver2.planeletter.dto.response.comment;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.dto.response.PaginationDto;
import org.example.crossoverserver2.planeletter.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.crossoverserver2.planeletter.dto.response.PaginationDto.paginationDto;

@Getter
@Builder
public class CommentListResponseData {
    private List<CommentDto> commentList;
    private PaginationDto paginationDto;

    public static CommentListResponseData commentListResponseData(Page<Comment> page){
        return CommentListResponseData.builder()
                .commentList(page.stream()
                        //CommentDto 형식으로 변환
                        .map(comment -> CommentDto.commentDto(comment))
                        .collect(Collectors.toList()))
                .paginationDto(PaginationDto.paginationDto(page))
                .build();
    }
}
