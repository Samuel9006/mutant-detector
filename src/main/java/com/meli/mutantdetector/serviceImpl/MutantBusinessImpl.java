package com.meli.mutantdetector.serviceImpl;

import com.meli.mutantdetector.service.IMutantBussiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.meli.mutantdetector.utils.Constants.MUTANT_PATTERN;

@Slf4j
@Component
public class MutantBusinessImpl implements IMutantBussiness {

    @Value("${dna.coincidence-count}")
    private int coincidenceCount;

    @Value("${dna.minimum-coincidences}")
    private int minimumCoincidences;

    @Override
    public int countOfCoincidences(List<String> dna) {
        int coincidencesInLine = this.findInLine(dna);
        if(!(coincidencesInLine >= this.minimumCoincidences)){
            coincidencesInLine += this.findInLine(this.findVerticalDiagonal(dna));
        }
        return coincidencesInLine;
    }


    /**
     * Find secuences in lines
     *
     * @param lines dna lines where you can search secuences
     * @return coincidences count in lines
     */
    private int findInLine(List<? extends CharSequence> lines) {
        return lines.stream().mapToInt(line -> (int) MUTANT_PATTERN.matcher(line).results().count()).sum();
    }


    /**
     * Find lines vertical or diagonal
     *
     * @param lines dna lines where you can search secuences
     * @return coincidences count in lines
     */
    private List<StringBuilder> findVerticalDiagonal(List<String> lines) {
        int length = lines.size();
        int diagonalLines = (length * 2) - 1;
        int midPoint = (diagonalLines / 2) + 1;
        List<StringBuilder> verticalDiagonalList = lines.stream().map(s -> new StringBuilder()).collect(Collectors.toList());
        int itemsInDiagonal = 0;

        for (int i = 1; i <= diagonalLines; i++) {
            int indexRow;
            int indexColumnMainDiagonal;
            int indexColumnInverseDiagonal;
            StringBuilder mainDiagonal = new StringBuilder();
            StringBuilder inverseDiagonal = new StringBuilder();

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    indexRow = (i - j) - 1;
                    indexColumnMainDiagonal = j;
                    indexColumnInverseDiagonal = length - 1 - j;
                    mainDiagonal.append(lines.get(indexRow).charAt(indexColumnMainDiagonal));
                    inverseDiagonal.append(lines.get(indexRow).charAt(indexColumnInverseDiagonal));
                    verticalDiagonalList.get(j).append(lines.get(indexRow).charAt(indexColumnMainDiagonal));
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    indexRow = (length - 1) - j;
                    indexColumnMainDiagonal = (i - length) + j;
                    indexColumnInverseDiagonal = ((length - i) + length - 1) - j;
                    mainDiagonal.append(lines.get(indexRow).charAt(indexColumnMainDiagonal));
                    inverseDiagonal.append(lines.get(indexRow).charAt(indexColumnInverseDiagonal));
                    verticalDiagonalList.get(indexColumnMainDiagonal).append(lines.get(indexRow).charAt(indexColumnMainDiagonal));
                }
            }

            if (itemsInDiagonal >= this.coincidenceCount) {
                verticalDiagonalList.add(mainDiagonal);
                verticalDiagonalList.add(inverseDiagonal);
            }
        }
        return verticalDiagonalList;
    }
}
