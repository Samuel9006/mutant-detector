package com.meli.mutantdetector.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutantdetector.dto.DnaRequestDTO;
import com.meli.mutantdetector.exception.MutantException;
import com.meli.mutantdetector.service.IMutantService;
import com.meli.mutantdetector.utils.UtilMutant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/mutant")
public class MutantController {

    private final IMutantService mutantService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> isMutant(@Valid @RequestBody DnaRequestDTO dnaRequestDTO, BindingResult result) {
        Map<String, Object> message = new HashMap<>();
        boolean isMutant;

        if(result.hasErrors()){
            message.put("errors", UtilMutant.getErrors(result));
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        try {
            this.mutantService.validateDna(dnaRequestDTO.getDna());
            log.info("Dna Service is starting");
            isMutant = this.mutantService.isMutant(dnaRequestDTO.getDna());
            log.info("The person is mutant: " + isMutant);

        } catch (MutantException e) {
            message.put("errors", e.getErrorMessage());
            return new ResponseEntity<>(message, e.getStatusCode());
        }

        if (!isMutant) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
