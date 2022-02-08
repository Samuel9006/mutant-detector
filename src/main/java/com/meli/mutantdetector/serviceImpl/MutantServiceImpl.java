package com.meli.mutantdetector.serviceImpl;

import com.meli.mutantdetector.exception.MutantException;
import com.meli.mutantdetector.model.entity.DnaAnalysis;
import com.meli.mutantdetector.model.repository.DnaAnalysisRepository;
import com.meli.mutantdetector.service.IMutantBussiness;
import com.meli.mutantdetector.service.IMutantService;
import com.meli.mutantdetector.utils.UtilMutant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.meli.mutantdetector.utils.Constants.ERROR_DNA_BAD_STRUCTURE_MESSAGE;

@Slf4j
@Service
public class MutantServiceImpl implements IMutantService {

    @Value("${dna.minimum-coincidences}")
    private int minimumCoincidences;

    @Autowired
    private IMutantBussiness mutantBussiness;

    @Autowired
    private DnaAnalysisRepository dnaAnalysisRepository;

    @Override
    public boolean isMutant(List<String> dna) {
        UtilMutant.showMatriz(dna);
        int countOfCoincidences = this.mutantBussiness.countOfCoincidences(dna);
        boolean isMutant  = countOfCoincidences >= this.minimumCoincidences;

        this.saveAnalysis(dna, isMutant);

        return  isMutant;
    }

    private void saveAnalysis(List<String> dna, boolean isMutant) {
        DnaAnalysis dnaAnalysis = new DnaAnalysis();
        dnaAnalysis.setDna(dna.toString());
        dnaAnalysis.setMutant(isMutant);
        this.dnaAnalysisRepository.save(dnaAnalysis);
    }

    @Override
    public void validateDna(List<String> dna) throws MutantException {

        int dnaLength = dna.size();
        Predicate<String> differentSize = line -> line.length() != dnaLength;
        Optional<String> badLine = dna.stream().filter(differentSize).findFirst();

        badLine.ifPresent(line -> {
            throw MutantException.builder()
                    .errorMessage(ERROR_DNA_BAD_STRUCTURE_MESSAGE)
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build();
        });

    }
}
