package com.getbridge.homework.rest.controller;


import com.getbridge.homework.rest.config.MyServletRequestWrapper;
import com.getbridge.homework.rest.dto.Search1on1Dto;
import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.dto.OneOnOneDto;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.service.Service;
import com.getbridge.homework.rest.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
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
    public ResponseEntity<OneOnOne> createOneOnOne(@RequestBody OneOnOneDto oneOnOne, HttpServletRequest request) {
        String userId = (String) request.getAttribute("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        OneOnOne newOneOnOne = oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOne));
        return ResponseEntity.ok(newOneOnOne);
    }

    @GetMapping("/getall1on1s")
    public ResponseEntity<Iterable<OneOnOne>> getAllOneOnOnes() {
        Iterable<OneOnOne> oneOnOnes = oneOnOneRepository.findAll();
        return ResponseEntity.ok(oneOnOnes);
    }

    @GetMapping("/get1on1byid/{oneOnOneId}")
    public ResponseEntity<OneOnOne> getOneOnOneById(@PathVariable String oneOnOneId, HttpServletRequestWrapper request) throws IllegalAccessException {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<OneOnOne> oneOnOne = oneOnOneRepository.findById(oneOnOneId);

        List<String> ids = oneOnOne.map(OneOnOne::getParticipantIds).orElse(null);
        if(ids != null && ids.contains(userId)) {
            return ResponseEntity.ok(oneOnOne.get());
        } else {
            throw new IllegalAccessException("user not in 1on1");
        }

    }

    @PutMapping("/update1on1/{oneOnOneId}")
    public ResponseEntity<OneOnOne> updateOneOnOne(@PathVariable String oneOnOneId, @RequestBody OneOnOneDto oneOnOneDto, HttpServletRequestWrapper request) throws IllegalAccessException {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        OneOnOne oneOnOne = oneOnOneRepository.findById(oneOnOneId).orElseThrow(() -> new IllegalAccessException("1on1 not found"));
        if(!oneOnOne.isConcluded()) {
            oneOnOne = service.update1on1(oneOnOneId, oneOnOneDto);
            return oneOnOne.getId() == "empty" ? ResponseEntity.notFound().build() : ResponseEntity.ok(oneOnOne);
        } else {
            throw new IllegalAccessException("1on1 is concluded-readonly");
        }
    }

    @DeleteMapping("/deleteoneonone/{oneOnOneId}")
    public ResponseEntity<Void> deleteOneOnOne(@PathVariable String oneOnOneId, MyServletRequestWrapper request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(oneOnOneId);
        if (existingOneOnOne.isPresent()) {
            oneOnOneRepository.deleteById(oneOnOneId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/conclude/{oneOnOneId}")
    public ResponseEntity<OneOnOne> concludeOneOnOne(@PathVariable String oneOnOneId, HttpServletRequest request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
            return ResponseEntity.ok(service.conclude1on1(oneOnOneId));
    }



    @PostMapping("search/1on1s")
    public List<OneOnOne> search1on1s (@RequestBody Search1on1Dto search1on1Dto, HttpServletRequest request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        if (userId == null) {
            return null;
        }
        return service.search(search1on1Dto);

    }



}
