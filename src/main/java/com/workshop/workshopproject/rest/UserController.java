package com.workshop.workshopproject.rest;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @PostMapping("/auth")
    public void getUsername(@RequestHeader Map<String, Object> json) {

    }
}
