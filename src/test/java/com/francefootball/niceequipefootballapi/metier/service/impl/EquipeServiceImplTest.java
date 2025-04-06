package com.francefootball.niceequipefootballapi.metier.service.impl;

import com.francefootball.niceequipefootballapi.metier.dto.EquipeDto;
import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;
import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import com.francefootball.niceequipefootballapi.metier.exception.EquipeCRUDException;
import com.francefootball.niceequipefootballapi.metier.exception.ResourceNotFoundException;
import com.francefootball.niceequipefootballapi.persistence.model.Equipe;
import com.francefootball.niceequipefootballapi.persistence.model.Joueur;
import com.francefootball.niceequipefootballapi.persistence.repository.EquipeRepository;
import com.francefootball.niceequipefootballapi.persistence.repository.JoueurRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.francefootball.niceequipefootballapi.metier.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;
    @Mock
    private JoueurRepository joueurRepository;
    private AutoCloseable autoCloseable;

    private EquipeServiceImpl equipeService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        equipeService = new EquipeServiceImpl(equipeRepository, joueurRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldTestGetAllEquipes() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "nomEquipe";
        String sortDirection = "asc";

        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1L);
        equipe.setNomEquipe("Equipe A");
        equipe.setAcronym("EA");
        equipe.setBudget(BigDecimal.valueOf(1000000));
        Joueur joueur = new Joueur();
        joueur.setPosition(PositionJoueur.AILIER_DROIT);
        joueur.setIdJoueur(1L);
        joueur.setNomJoueur("Joueur A");
        equipe.setJoueurs(List.of(joueur));

        // When
        when(equipeRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(equipe)));

        Page<EquipeDto> equipeDtos = equipeService.getAllEquipes(page, size, sortBy, sortDirection);

        // Then
        assertNotNull(equipeDtos);
        assertEquals(1, equipeDtos.getTotalElements());
        assertEquals("EA", equipeDtos.getContent().getFirst().acronym());
        assertEquals("Equipe A", equipeDtos.getContent().getFirst().nom());
        assertEquals(1, equipeDtos.getContent().getFirst().joueursDtos().size());
    }

    @Test
    void shouldThrowsExceptionWhenGetAllEquipes() {
        // Given
        int page = 0;
        int size = 10;
        String sortBy = "nomEquipe";
        String sortDirection = "asc";

        // When
        when(equipeRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            equipeService.getAllEquipes(page, size, sortBy, sortDirection);
        });

        // Then
        assertEquals(AUCUNE_EQUIPE_TROUVEE, exception.getMessage());
    }

    @Test
    void shouldTestEnregistrerEquipe() {
        // Given
        EquipeDto equipeDto = new EquipeDto(
                1L,
                "Equipe A",
                "EA",
                BigDecimal.valueOf(1000000),
                List.of(new JoueursDto(1L, "Joueur A", PositionJoueur.AILIER_DROIT.getLibelle()))
        );

        // When
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1L);
        when(equipeRepository.save(any())).thenReturn(equipe);
        Long idEquipe = equipeService.enregistrerEquipe(equipeDto);

        // Then
        assertNotNull(idEquipe);
    }

    @Test
    void shouldThrowExceptionEquipeExisteWhenEnregistrerEquipe() {
        // Given
        EquipeDto equipeDto = new EquipeDto(
                1L,
                "Equipe A",
                "EA",
                BigDecimal.valueOf(1000000),
                List.of(new JoueursDto(1L, "Joueur A", PositionJoueur.AILIER_DROIT.getLibelle()))
        );

        // When
        when(equipeRepository.existsById(any())).thenReturn(true);
        EquipeCRUDException exception = assertThrows(EquipeCRUDException.class, () -> {
            equipeService.enregistrerEquipe(equipeDto);
        });

        // Then
        assertEquals(EQUIPE_EXISTE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionJoueurAEquipeWhenEnregistrerEquipe() {
        // Given
        EquipeDto equipeDto = new EquipeDto(
                1L,
                "Equipe A",
                "EA",
                BigDecimal.valueOf(1000000),
                List.of(new JoueursDto(1L, "Joueur A", PositionJoueur.AILIER_DROIT.getLibelle()))
        );

        // When
        Joueur joueur = new Joueur();
        joueur.setPosition(PositionJoueur.AILIER_DROIT);
        joueur.setIdJoueur(1L);
        joueur.setNomJoueur("Joueur A");
        joueur.setEquipe(new Equipe());

        when(joueurRepository.findById(any())).thenReturn(Optional.of(joueur));
        EquipeCRUDException exception = assertThrows(EquipeCRUDException.class, () -> {
            equipeService.enregistrerEquipe(equipeDto);
        });

        // Then
        assertEquals(JOUEUR_A_EQUIPE, exception.getMessage());
    }

    @Test
    void shouldGetEquipeById() {
        // Given
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(1L);
        equipe.setNomEquipe("Equipe A");
        equipe.setAcronym("EA");
        equipe.setBudget(BigDecimal.valueOf(1000000));
        Joueur joueur = new Joueur();
        joueur.setPosition(PositionJoueur.AILIER_DROIT);
        joueur.setIdJoueur(1L);
        joueur.setNomJoueur("Joueur A");
        equipe.setJoueurs(List.of(joueur));

        // When
        when(equipeRepository.findById(any())).thenReturn(Optional.of(equipe));
        EquipeDto equipeDtos = equipeService.getEquipeById(1L);

        // Then
        assertNotNull(equipeDtos);
        assertEquals(1, equipeDtos.id());
        assertEquals("EA", equipeDtos.acronym());
        assertEquals("Equipe A", equipeDtos.nom());
    }

    @Test
    void shouldThrowExceptionWhenGetEquipeById() {
        // Given Nothing
        // When
        when(equipeRepository.findById(any())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            equipeService.getEquipeById(1L);
        });
        // Then
        assertEquals(AUCUNE_EQUIPE_TROUVEE, exception.getMessage());
    }
}
