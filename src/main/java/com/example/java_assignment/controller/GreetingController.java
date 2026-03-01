package com.example.java_assignment.controller;

import com.example.java_assignment.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/assignment")
public class GreetingController {
    @Autowired
    private GreetingService greetingService;

    @GetMapping("/hello-world")
    public ResponseEntity<Map<String, String>> getGreeting(@RequestParam(required = false) String name) {
        try {
            String message = greetingService.getGreeting(name);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }
}
