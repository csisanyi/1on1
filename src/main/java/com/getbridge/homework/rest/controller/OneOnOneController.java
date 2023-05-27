package com.getbridge.homework.rest.controller;

import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.entity.OneOnOneDto;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.service.Service;
import com.getbridge.homework.rest.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/1on1/")
public class OneOnOneController {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private Util util;

    @Autowired
    private Service service;

    public OneOnOneController(OneOnOneRepository oneOnOneRepository, Util util, Service service) {
        this.oneOnOneRepository = oneOnOneRepository;
        this.util = util;
        this.service = service;
    }

    @PostMapping("/create1on1")
    public ResponseEntity<OneOnOne> createOneOnOne(@RequestBody OneOnOneDto oneOnOne) {
        OneOnOne newOneOnOne = oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOne));
        return ResponseEntity.ok(newOneOnOne);
    }

    @GetMapping("/getall1on1s")
    public ResponseEntity<Iterable<OneOnOne>> getAllOneOnOnes() {
        Iterable<OneOnOne> oneOnOnes = oneOnOneRepository.findAll();
        return ResponseEntity.ok(oneOnOnes);
    }

    @GetMapping("/get1on1byid/{oneOnOneId}")
    public ResponseEntity<OneOnOne> getOneOnOneById(@PathVariable String oneOnOneId) {
        Optional<OneOnOne> oneOnOne = oneOnOneRepository.findById(oneOnOneId);
        return oneOnOne.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update1on1/{oneOnOneId}")
    public ResponseEntity<OneOnOne> updateOneOnOne(@PathVariable String oneOnOneId, @RequestBody OneOnOneDto oneOnOneDto) throws IllegalAccessException {
        OneOnOne oneOnOne = service.update1on1(oneOnOneId, oneOnOneDto);
        if(!oneOnOne.isConcluded()) {
            return oneOnOne.getId() == "empty" ? ResponseEntity.notFound().build() : ResponseEntity.ok(oneOnOne);
        } else {
            throw new IllegalAccessException("1on1 is concluded-readonly");
        }
    }

    @DeleteMapping("/deleteoneonone/{oneOnOneId}")
    public ResponseEntity<Void> deleteOneOnOne(@PathVariable String oneOnOneId) {
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(oneOnOneId);
        if (existingOneOnOne.isPresent()) {
            oneOnOneRepository.deleteById(oneOnOneId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/conclude/{oneOnOneId}")
    public ResponseEntity<OneOnOne> concludeOneOnOne(@PathVariable String oneOnOneId) {
            return ResponseEntity.ok(service.conclude1on1(oneOnOneId));
    }

}
