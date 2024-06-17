package org.example.crossoverserver2.planeletter.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponseData {
    private String name;
    private String title;
    private LocalDateTime content;
}