package com.meli.mutantdetector.service;

import com.meli.mutantdetector.exception.MutantException;
import com.meli.mutantdetector.utils.UtilMutant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.meli.mutantdetector.utils.Constants.ERROR_DNA_BAD_ESTRUCTURE_MESSAGE;

@Slf4j
@Service
public class MutantServiceImpl implements IMutantService {

    @Value("${dna.minimum-coincidences}")
    private int minimumCoincidences;

    @Autowired
    private IMutantBussiness mutantBussiness;

    @Override
    public boolean isMutant(List<String> dna) {
        UtilMutant.showMatriz(dna);
        int countOfCoincidences = this.mutantBussiness.countOfCoincidences(dna);
        return  countOfCoincidences >= this.minimumCoincidences;
    }

    @Override
    public void validateDna(List<String> dna) throws MutantException {

        int dnaLength = dna.size();
        Predicate<String> differentSize = line -> line.length() != dnaLength;
        Optional<String> badLine = dna.stream().filter(differentSize).findFirst();

        badLine.ifPresent(line -> {
            throw MutantException.builder()
                    .errorMessage(ERROR_DNA_BAD_ESTRUCTURE_MESSAGE)
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build();
        });

    }
}
