package com.getbridge.homework.rest.repository;

import com.getbridge.homework.rest.dto.Search1on1Dto;
import com.getbridge.homework.rest.entity.OneOnOne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OneOnOneRepository extends MongoRepository<OneOnOne, String> {

    @Query("{ 'title': { $in: [ '', ?#{#search1on1Dto.title} ] }, " +
            "'plannedDate': { $gte: ?#{#search1on1Dto.startDate}, $lte: ?#{#search1on1Dto.endDate} }, " +
            "'concluded': ?#{#search1on1Dto.closed} }")
    Optional<List<OneOnOne>> search(Search1on1Dto search1on1Dto);





}
