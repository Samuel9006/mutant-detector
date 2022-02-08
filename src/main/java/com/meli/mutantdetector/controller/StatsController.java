package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.service.IStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/stats")
public class StatsController {

    @Autowired
    private IStatsService statsService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getStats(){
        log.info("Stats Service is starting");
        return ResponseEntity.ok(statsService.getStats());
    }
}
