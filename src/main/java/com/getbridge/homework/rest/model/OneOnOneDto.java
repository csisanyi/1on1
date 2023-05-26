package com.getbridge.homework.rest.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OneOnOneDto {
    private String title;
    private List<String> participantIds;
    private LocalDateTime plannedDate;
    private String description;
    private String location;

    // Constructors, getters, and setters

    public OneOnOneDto() {
    }

    public OneOnOneDto(String title, List<String> participantIds, LocalDateTime plannedDate, String description, String location) {
        this.title = title;
        this.participantIds = participantIds;
        this.plannedDate = plannedDate;
        this.description = description;
        this.location = location;
    }
}
