package com.francefootball.niceequipefootballapi.controller;

import com.francefootball.niceequipefootballapi.config.TestSecurityConfig;
import com.francefootball.niceequipefootballapi.config.security.JwtAuthenticationFilter;
import com.francefootball.niceequipefootballapi.metier.service.impl.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_POSITION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PositionController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnListOfPositions() throws Exception {
        mockMvc.perform(get(PATH_POSITION))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
