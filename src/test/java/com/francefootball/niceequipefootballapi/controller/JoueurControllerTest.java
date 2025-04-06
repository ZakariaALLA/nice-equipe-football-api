package com.francefootball.niceequipefootballapi.controller;

import com.francefootball.niceequipefootballapi.config.TestSecurityConfig;
import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;
import com.francefootball.niceequipefootballapi.metier.service.interfaces.JoueurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_JOUEUR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JoueurController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class JoueurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JoueurService joueurService;

    @Test
    public void shouldReturnDetailsJoueurById() throws Exception {
        //Given
        when(joueurService.getDetailsJoueurById(any(Long.class))).thenReturn(new JoueursDto(1L, "Joueur 1", "Attaquant"));

        // When Then
        mockMvc.perform(get(PATH_JOUEUR + "/{idJoueur}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_joueur").value(1L))
                .andExpect(jsonPath("$.nom_joueur").value("Joueur 1"))
                .andExpect(jsonPath("$.position").value("Attaquant"));
    }
}
