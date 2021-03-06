package com.meli.mutantdetector.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (String row : matriz) {
            log.info(String.format(formatRow, row));
        }
    }


    public static Map<String, String> getErrors(BindingResult result) {
        Map<String, String> error = new HashMap<>();
        result.getFieldErrors().forEach(err -> error.put(err.getField(), err.getDefaultMessage()));
        return error;
    }
}
