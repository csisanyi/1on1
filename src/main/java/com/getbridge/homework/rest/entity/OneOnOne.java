package com.getbridge.homework.rest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "oneonones")
public class OneOnOne {
    @Id
    private String id;
    private String title;
    private List<User> participantIds;
    private LocalDateTime plannedDate;
    private String description;
    private String location;
    private boolean concluded = false;

    public OneOnOne(String title, List<User> participantIds, LocalDateTime plannedDate, String description, String location) {
        this.title = title;
        this.participantIds = participantIds;
        this.plannedDate = plannedDate;
        this.description = description;
        this.location = location;
    }

    public OneOnOne(String id) {
        this.id = id;
    }
}
