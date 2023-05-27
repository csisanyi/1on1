package com.getbridge.homework.rest.repository;

import com.getbridge.homework.rest.entity.OneOnOne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OneOnOneRepository extends MongoRepository<OneOnOne, String> {

}
