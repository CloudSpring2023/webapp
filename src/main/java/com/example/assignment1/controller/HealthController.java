package com.example.assignment1.controller;

import com.example.assignment1.entity.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/healthz")
    public ResponseEntity<?> getHealth() {
        return new ResponseEntity<UserDto>( HttpStatus.OK);
    }
}