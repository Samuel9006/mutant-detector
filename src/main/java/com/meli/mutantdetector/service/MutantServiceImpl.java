package com.meli.mutantdetector.service;

import com.meli.mutantdetector.utils.UtilMutant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public void validateDna(List<String> dna) throws Exception{

    }
}
