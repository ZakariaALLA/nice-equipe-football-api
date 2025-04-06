package com.francefootball.niceequipefootballapi.metier.service.impl;

import com.francefootball.niceequipefootballapi.metier.dto.LoginResponse;
import com.francefootball.niceequipefootballapi.metier.dto.UserDto;
import com.francefootball.niceequipefootballapi.persistence.model.Role;
import com.francefootball.niceequipefootballapi.persistence.model.User;
import com.francefootball.niceequipefootballapi.persistence.repository.RoleRepository;
import com.francefootball.niceequipefootballapi.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    private AutoCloseable autoCloseable;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(
                userRepository, roleRepository,
                passwordEncoder, authenticationManager, jwtService
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldTestSignup() {
        // Given
        UserDto userDto = new UserDto("testUser", "testPassword");
        userDto.setRoles(Set.of("USER"));

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName(any())).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(any())).thenReturn("UserPassword");

        // Then
        LoginResponse loginResponse = authenticationService.signup(userDto);
        assertNotNull(loginResponse);
    }

}
