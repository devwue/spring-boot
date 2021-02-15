package com.devwue.spring.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;

@ApiIgnore
@RestController
public class HealthController {
    @PersistenceContext
    private EntityManager em;

    @GetMapping("/health")
    public ResponseEntity health() {
        try {
            Query query = em.createNativeQuery("select 1");
            query.getSingleResult();
            return ResponseEntity.ok().body(Collections.singletonMap("health", "ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("health", "failed"));
        }
    }

}
