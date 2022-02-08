package com.meli.mutantdetector.model.repository;

import com.meli.mutantdetector.model.entity.DnaAnalysis;
import org.springframework.data.repository.CrudRepository;

public interface DnaAnalysisRepository extends CrudRepository<DnaAnalysis, Long> {
}
