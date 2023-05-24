package com.getbridge.homework.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneOnOneRepository extends MongoRepository<OneOnOneRepository, String> {


}
