package com.francefootball.niceequipefootballapi.metier.service.impl;

import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;
import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import com.francefootball.niceequipefootballapi.metier.exception.ResourceNotFoundException;
import com.francefootball.niceequipefootballapi.persistence.model.Joueur;
import com.francefootball.niceequipefootballapi.persistence.repository.JoueurRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.francefootball.niceequipefootballapi.metier.constants.ErrorMessages.AUCUN_JOUEUR_TROUVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JoueurServiceImplTest {
    @Mock
    private JoueurRepository joueurRepository;
    private AutoCloseable autoCloseable;

    private JoueurServiceImpl joueurService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        joueurService = new JoueurServiceImpl(joueurRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldTestGetAllEquipes() {
        // Given
        Joueur joueur = new Joueur();
        joueur.setPosition(PositionJoueur.AILIER_DROIT);
        joueur.setIdJoueur(1L);
        joueur.setNomJoueur("Joueur A");

        // When
        when(joueurRepository.findById(any())).thenReturn(Optional.of(joueur));
        JoueursDto joueursDto = joueurService.getDetailsJoueurById(1L);

        // Then
        assertNotNull(joueursDto);
        assertEquals(1, joueursDto.idJoueur());
        assertEquals("Joueur A", joueursDto.nomJoueur());
        assertEquals(PositionJoueur.AILIER_DROIT.getLibelle(), joueursDto.position());
    }

    @Test
    void shouldThrowExceptionWhenGetAllEquipes() {
        // Given Nothing

        // When
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            joueurService.getDetailsJoueurById(1L);
        });

        // Then
        assertEquals(AUCUN_JOUEUR_TROUVE, exception.getMessage());
    }

}
