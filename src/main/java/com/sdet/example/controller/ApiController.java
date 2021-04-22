package com.sdet.example.controller;

import com.sdet.example.model.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/")
    ApiResponse get() {
        return new ApiResponse();
    }

}
