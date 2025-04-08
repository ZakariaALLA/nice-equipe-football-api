package com.francefootball.niceequipefootballapi.config.db;

import com.francefootball.niceequipefootballapi.metier.enums.EnumRoles;
import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import com.francefootball.niceequipefootballapi.persistence.model.Equipe;
import com.francefootball.niceequipefootballapi.persistence.model.Joueur;
import com.francefootball.niceequipefootballapi.persistence.model.Role;
import com.francefootball.niceequipefootballapi.persistence.repository.EquipeRepository;
import com.francefootball.niceequipefootballapi.persistence.repository.JoueurRepository;
import com.francefootball.niceequipefootballapi.persistence.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Transactional
@AllArgsConstructor
@ConditionalOnExpression("${data.initialize.enabled:false}")
@Slf4j
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final EquipeRepository equipeRepository;
    private final JoueurRepository joueurRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeRoles() {
        log.info("Initializing roles...");
        if (roleRepository.count() == 0) {
            log.info("Creating roles...");
            Role user = new Role();
            user.setName(EnumRoles.ROLE_USER);
            Role admin = new Role();
            admin.setName(EnumRoles.ROLE_ADMIN);
            roleRepository.saveAll(List.of(user, admin));
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        initializeEquipes();
        initializeJoueurs();
    }

    private void initializeEquipes() {
        if (equipeRepository.count() == 0) {
            List<Equipe> equipes = List.of(
                    createEquipe("Les Aiglons de Nice", "AIG", BigDecimal.valueOf(1000000)),
                    createEquipe("Les Bleus de Nice", "BLE", BigDecimal.valueOf(1500000)),
                    createEquipe("Les Canaris de Nice", "CAN", BigDecimal.valueOf(1200000)),
                    createEquipe("Les Ducs de Nice", "DUC", BigDecimal.valueOf(1100000)),
                    createEquipe("Les Étoiles de Nice", "ETO", BigDecimal.valueOf(1300000))
            );
            equipeRepository.saveAll(equipes);
        }
    }

    private Equipe createEquipe(String nomEquipe, String acronym, BigDecimal budget) {
        Equipe equipe = new Equipe();
        equipe.setNomEquipe(nomEquipe);
        equipe.setAcronym(acronym);
        equipe.setBudget(budget);
        return equipe;
    }

    private void initializeJoueurs() {
        if (joueurRepository.count() == 0) {
            List<Joueur> joueurs = List.of(
                    createJoueur("Jean Dupont", PositionJoueur.ARRIERE_DROIT, 1L),
                    createJoueur("Pierre Martin", PositionJoueur.MILIEU_DEFENSIF, 2L),
                    createJoueur("Luc Durand", PositionJoueur.ARRIERE_GAUCHE, 3L),
                    createJoueur("Marc Bernard", PositionJoueur.DEFENSEUR_CENTRAL, 4L),
                    createJoueur("Paul Lefevre", PositionJoueur.LIBERO, 5L)
            );
            joueurRepository.saveAll(joueurs);
        }
    }

    private Joueur createJoueur(String nomJoueur, PositionJoueur position, Long equipeId) {
        Joueur joueur = new Joueur();
        joueur.setNomJoueur(nomJoueur);
        joueur.setPosition(position);
        joueur.setEquipe(equipeRepository.findById(equipeId).orElse(null));
        return joueur;
    }
}
