package com.meli.mutantdetector.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UtilMutant {

    /**
     * Show matriz
     *
     * @param matriz matriz to show
     */
    public static void showMatriz(List<String> matriz) {
        log.info("This is the dna matriz: ");
        String formatRow = "%s ";
        for(String row : matriz){
            log.info(String.format(formatRow, row));
        }
    }
}
