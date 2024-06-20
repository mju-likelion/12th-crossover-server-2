package org.example.crossoverserver2.planeletter.dto.response.comment;

import lombok.Builder;
import lombok.Getter;
import org.example.crossoverserver2.planeletter.dto.response.PaginationDto;

import java.util.List;

@Getter
@Builder
public class CommentListResponseData {
    private List<CommentDto> commentList;
    private PaginationDto paginationDto;
}
