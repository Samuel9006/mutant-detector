package com.meli.mutantdetector.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "dna_analysis")
public class DnaAnalysis {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idAnalysis;

    @Column
    private String dna;

    @Column
    private boolean isMutant;
}
