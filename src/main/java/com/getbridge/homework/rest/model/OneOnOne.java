package com.getbridge.homework.rest.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "oneonones")
public class OneOnOne {
    @Id
    private String id;
    private String title;
    private List<String> participantIds;
    private Date plannedDate;
    private String description;
    private String location;

    public OneOnOne(String title, List<String> participantIds, Date plannedDate, String description, String location) {
        this.title = title;
        this.participantIds = participantIds;
        this.plannedDate = plannedDate;
        this.description = description;
        this.location = location;
    }
}
