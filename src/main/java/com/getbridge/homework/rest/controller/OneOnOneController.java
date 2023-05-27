package com.getbridge.homework.rest.controller;

import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.entity.OneOnOneDto;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.util.Util;
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

    public OneOnOneController(OneOnOneRepository oneOnOneRepository, Util util) {
        this.oneOnOneRepository = oneOnOneRepository;
        this.util = util;
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
    public ResponseEntity<OneOnOne> updateOneOnOne(@PathVariable String oneOnOneId, @RequestBody OneOnOneDto oneOnOneDto) {
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(oneOnOneId);
        if (existingOneOnOne.isPresent()) {
            OneOnOne oneOnOne = util.convertOneOnOneDtoToEntity(oneOnOneDto);
            OneOnOne updatedOneOnOne = oneOnOneRepository.save(oneOnOne);
            return ResponseEntity.ok(updatedOneOnOne);
        } else {
            return ResponseEntity.notFound().build();
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

}
