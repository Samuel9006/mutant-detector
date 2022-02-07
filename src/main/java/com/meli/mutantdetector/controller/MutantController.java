package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.dto.DnaRequestDTO;
import com.meli.mutantdetector.service.IMutantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/mutant")
public class MutantController {

    private final IMutantService mutantService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<Object> isMutant(@RequestBody DnaRequestDTO dnaRequestDTO){

        log.info("Dna Service is starting");
        boolean isMutant = this.mutantService.isMutant(dnaRequestDTO.getDna());
        log.info("The person is mutant: "+isMutant);

        if(!isMutant){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
