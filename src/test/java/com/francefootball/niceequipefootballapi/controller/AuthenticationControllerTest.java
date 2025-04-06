package com.francefootball.niceequipefootballapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.francefootball.niceequipefootballapi.config.TestSecurityConfig;
import com.francefootball.niceequipefootballapi.metier.dto.LoginResponse;
import com.francefootball.niceequipefootballapi.metier.dto.UserDto;
import com.francefootball.niceequipefootballapi.metier.dto.UserLoginDto;
import com.francefootball.niceequipefootballapi.metier.service.impl.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_AUTH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationService authenticationService;

    @Test
    void register() throws Exception {
        //Given
        UserDto userDto = new UserDto("testUser", "testPassword");
        userDto.setRoles(Set.of("USER"));

        when(authenticationService.signup(any(UserDto.class))).thenReturn(
                new LoginResponse("MyToken", 360_000)
        );

        // When Then
        mockMvc.perform(post(PATH_AUTH + "/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("MyToken"))
                .andExpect(jsonPath("$.expiresIn").value(360_000));
    }

    @Test
    void authenticate() throws Exception {

        //Given
        UserLoginDto userLoginDto = new UserLoginDto("testUser", "testPassword");

        when(authenticationService.authenticate(any(UserLoginDto.class))).thenReturn(
                new LoginResponse("MyToken", 360_000)
        );

        // When Then
        mockMvc.perform(post(PATH_AUTH + "/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userLoginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("MyToken"))
                .andExpect(jsonPath("$.expiresIn").value(360_000));
    }
}
