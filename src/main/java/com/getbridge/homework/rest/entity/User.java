package com.getbridge.homework.rest.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    @Indexed
    private String email;
    private String password;

    public User(String name, String email, String password) {
        id = new ObjectId().toString();
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

