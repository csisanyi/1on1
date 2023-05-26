package com.getbridge.homework.rest.repository;

import com.getbridge.homework.rest.model.OneOnOne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneOnOneRepository extends MongoRepository<OneOnOne, String> {

}
