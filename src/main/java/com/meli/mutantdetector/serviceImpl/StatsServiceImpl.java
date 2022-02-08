package com.meli.mutantdetector.serviceImpl;

import com.meli.mutantdetector.dto.StatsDTO;
import com.meli.mutantdetector.model.repository.DnaAnalysisRepository;
import com.meli.mutantdetector.service.IStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatsServiceImpl implements IStatsService {

    @Autowired
    private DnaAnalysisRepository dnaAnalysisRepository;


    /**
     * get the stats about DNA's analysis
     * @return Return an object with result of analysis
     * */
    @Override
    public StatsDTO getStats() {
        int countMutants = dnaAnalysisRepository.getCountDnaAnalysis(true);
        int countHumans = dnaAnalysisRepository.getCountDnaAnalysis(false);
        BigDecimal ratio;
        try {
            ratio = new BigDecimal(countMutants).divide(new BigDecimal(countHumans));
        }catch (ArithmeticException ex){
            ratio = BigDecimal.valueOf(countMutants);
        }

        return StatsDTO.builder()
                .mutantCountDna(countMutants)
                .humanCountDna(countHumans)
                .ratio(ratio)
                .build();
    }
}
