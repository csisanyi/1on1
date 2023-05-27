package com.getbridge.homework.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Search1on1Dto {

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isClosed = false;

    public Search1on1Dto(String title, LocalDateTime startDate, LocalDateTime endDate, boolean isClosed) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isClosed = isClosed;
    }
}
