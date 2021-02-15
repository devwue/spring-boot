package com.devwue.spring.api.controller.test;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Api(tags = "test")
@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping("")
    public ResponseEntity index() {
        HashMap<String, String> result = new HashMap<>();
        result.put("key", "test");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/exception")
    public void throwSentryException() {
        throw new IllegalArgumentException("setting test", new Exception("test1"));
    }
}
