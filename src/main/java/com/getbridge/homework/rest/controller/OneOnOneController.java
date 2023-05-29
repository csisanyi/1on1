package com.getbridge.homework.rest.controller;


import com.getbridge.homework.rest.dto.Search1on1Dto;
import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.dto.OneOnOneDto;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.service.Service;
import com.getbridge.homework.rest.service.util.Util;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


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
    public ResponseEntity<OneOnOne> createOneOnOne(@RequestBody OneOnOneDto oneOnOne, HttpServletRequestWrapper request) {
        String userId = (String) request.getAttribute("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        OneOnOne newOneOnOne = oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOne));
        return ResponseEntity.ok(newOneOnOne);
    }

    @GetMapping("/getall1on1sofuser/")
    public ResponseEntity<Iterable<OneOnOne>> getAllOneOnOnes(HttpServletRequestWrapper request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        Set<OneOnOne> oneOnOnes = oneOnOneRepository.findAll().stream().filter(_1on1 -> _1on1.getParticipantIds().contains(userId)).collect(Collectors.toSet());
        return ResponseEntity.ok(oneOnOnes);
    }

    @ApiResponse(description = "User is not part of the requested 1on1", responseCode = "401")
    @GetMapping("/get1on1byid/{oneOnOneId}")
    public ResponseEntity<OneOnOne> getOneOnOneById(@PathVariable String oneOnOneId, HttpServletRequestWrapper request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        Optional<OneOnOne> oneOnOne = oneOnOneRepository.findById(oneOnOneId);

        List<String> ids = oneOnOne.map(OneOnOne::getParticipantIds).orElse(null);
        if(ids != null && ids.contains(userId)) {
            return ResponseEntity.ok(oneOnOne.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    @ApiResponse(description = "1on1 is cocluded", responseCode = "401")
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @ApiResponse(description = "User is not part of the requested 1on1", responseCode = "401")
    @DeleteMapping("/deleteoneonone/{oneOnOneId}")
    public ResponseEntity<Void> deleteOneOnOne(@PathVariable String oneOnOneId, HttpServletRequestWrapper request){
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(oneOnOneId);
        if (existingOneOnOne.isPresent()) {
            if(existingOneOnOne.get().getParticipantIds().contains(userId)) {
                oneOnOneRepository.deleteById(oneOnOneId);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiResponse(description = "User is not part of the requested 1on1", responseCode = "401")
    @PutMapping("/conclude/{oneOnOneId}")
    public ResponseEntity<OneOnOne> concludeOneOnOne(@PathVariable String oneOnOneId, HttpServletRequestWrapper request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        Optional<OneOnOne> existingOneOnOne = oneOnOneRepository.findById(oneOnOneId);
        if (existingOneOnOne.isPresent()) {
            if (existingOneOnOne.get().getParticipantIds().contains(userId)) {
                return ResponseEntity.ok(service.conclude1on1(oneOnOneId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }



    @PostMapping("search/1on1s")
    public List<OneOnOne> search1on1s (@RequestBody Search1on1Dto search1on1Dto, HttpServletRequestWrapper request) {
        String userId = request.getHeader("X-AUTHENTICATED-USER");
        return service.search(search1on1Dto).stream().filter(res -> res.getParticipantIds().contains(userId)).collect(Collectors.toList());

    }



}
