package com.meli.mutantdetector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.meli.mutantdetector.utils.Constants.ERROR_CHARACTERS_NOT_ALLOW_MESSAGE;
import static com.meli.mutantdetector.utils.Constants.ERROR_DNA_EMPTY_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DnaRequestDTO {

    @NotNull
    @NotEmpty(message = ERROR_DNA_EMPTY_MESSAGE)
    private List<@Pattern(regexp = "[ATCG]+", flags = Pattern.Flag.CASE_INSENSITIVE, message = ERROR_CHARACTERS_NOT_ALLOW_MESSAGE) String> dna;
}
