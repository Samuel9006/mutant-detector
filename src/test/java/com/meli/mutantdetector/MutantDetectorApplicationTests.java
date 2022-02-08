package com.meli.mutantdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutantdetector.dto.DnaRequestDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meli.mutantdetector.utils.Constants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutantDetectorApplicationTests {

    private static final String DNA_CHARACTERS_NOT_VALID = "chars_not_valid";
    private static final String DNA_EMPTY = "empty_dna";
    private static final String DNA_NOT_VALID = "dna_not_valid";
    private static final String DNA_LINES_MUTANT = "dna_mutant";
    private static final String DNA_LINES_HUMAN = "dna_human";
    private static final String DNA_LINES_MUTANT_HORIZONTAL = "dna_mutant_hor";

    @Autowired
    private WebApplicationContext appContext;

    private MockMvc mockMvc;

    private Map<String, List<String>> dnaTest;

    @BeforeAll
    public void init() {
        dnaTest = new HashMap<>();

        dnaTest.put(DNA_CHARACTERS_NOT_VALID,  List.of("ZTGC", "DAG2", "D3DD", "AAA1"));
        dnaTest.put(DNA_EMPTY,  List.of());
        dnaTest.put(DNA_NOT_VALID,  List.of("ATGC","ATGC","ATGC"));
        dnaTest.put(DNA_LINES_MUTANT,  List.of("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"));
        dnaTest.put(DNA_LINES_HUMAN,  List.of("ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"));
        dnaTest.put(DNA_LINES_MUTANT_HORIZONTAL,  List.of("AAAAAA","TTTTTT","TTATGT","AGAAGG","CCCCTA","TCACTG"));

        mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
    }


    @Test
    void ifDnaLineHasCharactersNotValid() throws Exception    {
        mockMvc.perform(post("/mutant")
                        .content(convertJsonString(DnaRequestDTO.builder()
                                        .dna(dnaTest.get(DNA_CHARACTERS_NOT_VALID))
                                        .build()
                                ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[0]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[1]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[2]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[3]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[0]']").value(ERROR_CHARACTERS_NOT_ALLOW_MESSAGE));    }


    @Test
    void ifDnaIsEmpty() throws Exception    {
        mockMvc.perform(post("/mutant")
                        .content(convertJsonString(DnaRequestDTO.builder()
                                .dna(dnaTest.get(DNA_EMPTY))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.dna").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.dna").value(ERROR_DNA_EMPTY_MESSAGE));
    }


    @Test
    void ifDnaIsNotValid() throws Exception    {
        mockMvc.perform(post("/mutant")
                        .content(convertJsonString(DnaRequestDTO.builder()
                                .dna(dnaTest.get(DNA_NOT_VALID))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value(ERROR_DNA_BAD_STRUCTURE_MESSAGE));
    }

    @Test
    void ifDnaIsMutant() throws Exception    {
        mockMvc.perform(post("/mutant")
                        .content(convertJsonString(DnaRequestDTO.builder()
                                .dna(dnaTest.get(DNA_LINES_MUTANT))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void ifDnaIsHuman() throws Exception    {
        mockMvc.perform(post("/mutant")
                        .content(convertJsonString(DnaRequestDTO.builder()
                                .dna(dnaTest.get(DNA_LINES_HUMAN))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void ifDnaMatchesInHorizontalLines() throws Exception    {
        mockMvc.perform(post("/mutant")
                        .content(convertJsonString(DnaRequestDTO.builder()
                                .dna(dnaTest.get(DNA_LINES_MUTANT_HORIZONTAL))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testGetStats() throws Exception {
        mockMvc.perform(get("/stats/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").exists());
    }

    static String convertJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
