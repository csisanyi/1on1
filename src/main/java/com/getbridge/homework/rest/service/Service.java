package com.getbridge.homework.rest.service;

import com.getbridge.homework.rest.dto.Search1on1Dto;
import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.dto.OneOnOneDto;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Service {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Util util;

    public Service(OneOnOneRepository oneOnOneRepository, MongoTemplate mongoTemplate, Util util) {
        this.oneOnOneRepository = oneOnOneRepository;
        this.mongoTemplate = mongoTemplate;
        this.util = util;
    }

    public OneOnOne update1on1(String id, OneOnOneDto oneOnOneDto) {
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(id);
        if (existingOneOnOne.isPresent()) {
            OneOnOne oneOnOne = util.convertOneOnOneDtoToEntity(oneOnOneDto);
            OneOnOne updatedOneOnOne = oneOnOneRepository.save(oneOnOne);
            return updatedOneOnOne;
        } else {
            throw new RuntimeException("1on1 Not Found");
        }
    }

    public OneOnOne conclude1on1(String id) {
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(id);
        if (existingOneOnOne.isPresent()) {
            existingOneOnOne.get().setConcluded(true);
            return oneOnOneRepository.save(existingOneOnOne.get());
        } else {
            throw new RuntimeException("1on1 Not Found");
        }
    }

    public List<OneOnOne> search(Search1on1Dto search1on1Dto) {
        Optional<List<OneOnOne>> matches= oneOnOneRepository.search(search1on1Dto);
        return matches.orElseGet(ArrayList::new);
    }


}
