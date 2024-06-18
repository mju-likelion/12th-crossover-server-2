package org.example.crossoverserver2.planeletter.dto.response.comment;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentListResponseData {
    private List<CommentDto> commentList;
}
