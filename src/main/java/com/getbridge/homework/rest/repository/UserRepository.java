package com.getbridge.homework.rest.repository;

import com.getbridge.homework.rest.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
