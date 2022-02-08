package com.meli.mutantdetector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsDTO {
    @JsonProperty("count_mutant_dna")
    private int mutantCountDna;
    @JsonProperty("count_human_dna")
    private int humanCountDna;
    private BigDecimal ratio;
}
