package com.meli.mutantdetector.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MutantServiceImpl implements IMutantService {

    @Value("${dna.coincidence-count}")
    private int coincidenceCount;

    @Override
    public boolean isMutant(String[] dna) {
        // TODO validar que tenga solo y unicamente las letras adecuadas considerar crear una excepción


        char[][] matriz = this.convertToMatriz(dna);
        this.showMatriz(matriz);
        return this.validateMutant(matriz);
    }

    private char[][] convertToMatriz(String[] dna) {
        char[][] rows = new char[dna.length][];
        for (int row = 0; row < dna.length; row++) {
            rows[row] = this.convertToCharArray(dna[row]);
        }
        return rows;
    }


    private char[] convertToCharArray(String word) {
        return word.toCharArray();
    }

    /**
     * Show the matriz
     *
     * @param matriz matriz to show
     */
    public void showMatriz(char[][] matriz) {
        log.info("This is the dna matriz: ");
        for (int row = 0; row < matriz.length; row++) {
            for (int col = 0; col < matriz[row].length; col++) {
                System.out.print(matriz[row][col] + " ");
            }
            System.out.println();
        }

    }

    private boolean validateMutant(char[][] matriz) {
        int secuenceCount;
        int coincidencesInRow = this.findInLine(matriz, false);
        int coincidencesInColumns = this.findInLine(matriz, true);
        int coincidencesInDiagonal = this.findInDiagonal(matriz);
        int coincidencesInInverseDiagonal = this.findInInverseDiagonal(matriz);

        secuenceCount = coincidencesInRow + coincidencesInColumns + coincidencesInDiagonal;
        log.info("secuence count: " + secuenceCount);
        return secuenceCount > 1 ;
    }

    /**
     * Find secuences in lines
     *
     * @param matriz        matriz where you can search secuences
     * @param orderByColumn lines can be horizontal or vertical
     * @return coincidences count in lines
     */
    private int findInLine(char[][] matriz, boolean orderByColumn) {

        int secuenceCount;
        int coincidences = 0;
        char letterToCompare;
        int height = matriz.length;
        int width = matriz[0].length;

        int firstAxis = !orderByColumn ? height : width;
        int secondAxis = !orderByColumn ? width : height;

        try {
            for (int row = 0; row < firstAxis; row++) {

                letterToCompare = !orderByColumn ? matriz[row][0] : matriz[0][row];
                secuenceCount = 1;
                for (int col = 1; col < secondAxis; col++) {

                    //if it doesn't match, continue with the next letter
                    char nextItem = !orderByColumn ? matriz[row][col] : matriz[col][row];
                    if (letterToCompare != nextItem) {
                        //change the letter to compare by the actual
                        letterToCompare = nextItem;
                        continue;
                    }

                    ++secuenceCount;

                    if (secuenceCount == this.coincidenceCount) {
                        coincidences++;
                        secuenceCount = 1;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("It was a error find in lines", ex);
        }

        return coincidences;
    }


    /**
     * Find secuences in diagonal
     *
     * @param matriz matriz where you can search secuences
     * @return secuence count in diagonal
     */
    private int findInDiagonal(char[][] matriz) {
        int secuenceCount;
        int coincidences = 0;
        char letterToCompare;
        int height = matriz.length;
        int width = matriz[0].length;

        try {
            for (int x = 0; x < height; x++) {

                letterToCompare = matriz[x][0];
                secuenceCount = 1;
                for (int y = 1; y < width; y++) {

                    //if it doesnt match, continue with the next letter
                    char nextItem = matriz[y][y];
                    if (letterToCompare != nextItem) {
                        //change the letter to compare by the actual
                        letterToCompare = nextItem;
                        continue;
                    }

                    ++secuenceCount;

                    if (secuenceCount == this.coincidenceCount) {
                        coincidences++;
                        secuenceCount = 1;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("It was a error find in lines", ex);
        }

        return coincidences;
    }

    /**
     * Find secuences in inverse diagonal
     *
     * @param matriz matriz where you can search secuences
     * @return secuence count in inverse diagonal
     */
    private int findInInverseDiagonal(char[][] matriz) {
        int secuenceCount;
        int coincidences = 0;
        char letterToCompare;

        letterToCompare = matriz[0][matriz[0].length - 1];
        secuenceCount = 1;

        for (int i = 1, j = 1; i < matriz.length; i++, j--) {


            //if it doesnt match, continue with the next letter
            if (letterToCompare != matriz[i][j]) {
                //change the letter to compare by the actual
                letterToCompare = matriz[i][j];
                continue;
            }

            ++secuenceCount;

            if (secuenceCount == this.coincidenceCount) {
                coincidences++;
                secuenceCount = 1;
            }
        }
        return coincidences;
    }
}
