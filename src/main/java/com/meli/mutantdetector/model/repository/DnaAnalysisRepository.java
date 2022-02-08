package com.meli.mutantdetector.model.repository;

import com.meli.mutantdetector.model.entity.DnaAnalysis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DnaAnalysisRepository extends CrudRepository<DnaAnalysis, Long> {

    @Query(
            "SELECT count(a) from DnaAnalysis as a " +
            "WHERE a.isMutant = :isMutant"
    )
    int getCountDnaAnalysis(@Param("isMutant") boolean isMutant);
}
