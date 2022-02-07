package com.meli.mutantdetector.service;

import java.util.List;

public interface IMutantService {

    boolean isMutant(List<String> dna);
    void validateDna(List<String> dna) throws Exception;

}
