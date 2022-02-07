package com.meli.mutantdetector.service;

import java.util.List;

public interface IMutantBussiness {
    int countOfCoincidences(List<String> dna);
}
