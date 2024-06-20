package org.example.crossoverserver2.planeletter.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaginationDto {
    private int totalPage;
    private int currentPage;
}
