package com.example.assignment1.controller;

import com.example.assignment1.entity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/healthz")
public class HealthController {

    @Autowired
    StatsDClient statsDClient;

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @GetMapping()
    public ResponseEntity<?> getHealth() {
        logger.info("This is Testing Get method for Checking Healthz ");
        statsDClient.incrementCounter("endpoint.getHealth.http.get");
        return new ResponseEntity<UserDto>( HttpStatus.OK);
    }
    
    @GetMapping(value = "/naiya")
    public ResponseEntity<?> getnaiya() {
        logger.info("This is Testing Get method for Checking Healthz ");
        statsDClient.incrementCounter("endpoint.getHealth.http.get");
        return new ResponseEntity<UserDto>( HttpStatus.OK);
    }
}
