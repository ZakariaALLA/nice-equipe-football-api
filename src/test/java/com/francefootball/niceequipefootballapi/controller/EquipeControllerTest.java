package com.francefootball.niceequipefootballapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francefootball.niceequipefootballapi.config.TestSecurityConfig;
import com.francefootball.niceequipefootballapi.metier.dto.EquipeDto;
import com.francefootball.niceequipefootballapi.metier.service.interfaces.EquipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_EQUIPE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EquipeController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class EquipeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EquipeService equipeService;

    @Test
    public void shouldReturnEquipeById() throws Exception {
        //Given
        when(equipeService.getEquipeById(any(Long.class))).thenReturn(
                new EquipeDto(1L, "Equipe 1", "E1", BigDecimal.valueOf(100000), List.of())
        );

        // When Then
        mockMvc.perform(get(PATH_EQUIPE + "/{idEquipe}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_equipe").value(1L))
                .andExpect(jsonPath("$.nom_equipe").value("Equipe 1"))
                .andExpect(jsonPath("$.acronym").value("E1"));
    }

    @Test
    public void shouldReturnListOfEquipe() throws Exception {
        //Given
        when(equipeService.getAllEquipes(anyInt(), anyInt(), anyString(), anyString())).thenReturn(new PageImpl<>(List.of()));

        // When Then
        mockMvc.perform(get(PATH_EQUIPE + "?page=0&size=5&sortBy=nomEquipe&sortDirection=ASC", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEnregistrerEquipe() throws Exception {
        //Given
        EquipeDto equipeDto = new EquipeDto(1L, "Equipe 1", "E1", BigDecimal.valueOf(100000), List.of());
        when(equipeService.enregistrerEquipe(any(EquipeDto.class))).thenReturn(1L);

        // When Then
        mockMvc.perform(post(PATH_EQUIPE)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(equipeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1L));
    }
}
