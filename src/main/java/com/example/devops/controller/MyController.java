package com.example.devops.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev-ops")
public class MyController {
    @GetMapping("/sayHello")
    public ResponseEntity<?> getHello(){
        return new ResponseEntity<>("<h1>Hello dev-ops</h1>", HttpStatus.OK);
    }
}
