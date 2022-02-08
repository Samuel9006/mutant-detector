package com.meli.mutantdetector.service;

import com.meli.mutantdetector.exception.MutantException;

import java.util.List;

public interface IMutantService {

    boolean isMutant(List<String> dna);
    void validateDna(List<String> dna) throws MutantException;

}
