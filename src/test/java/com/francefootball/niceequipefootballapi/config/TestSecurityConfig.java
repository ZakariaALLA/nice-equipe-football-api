package com.francefootball.niceequipefootballapi.config;

import com.francefootball.niceequipefootballapi.config.security.JwtAuthenticationFilter;
import com.francefootball.niceequipefootballapi.metier.service.impl.JwtService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    @Primary
    public JwtService jwtService() {
        return mock(JwtService.class);
    }

    @Bean
    @Primary
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return mock(JwtAuthenticationFilter.class);
    }
}
