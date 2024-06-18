package org.example.crossoverserver2.planeletter.dto.response.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaginationDto {
    private int totalPage;
    private int currentPage;
}
