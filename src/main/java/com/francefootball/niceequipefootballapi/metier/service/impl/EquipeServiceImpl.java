package com.francefootball.niceequipefootballapi.metier.service.impl;

import com.francefootball.niceequipefootballapi.metier.dto.EquipeDto;
import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;
import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import com.francefootball.niceequipefootballapi.metier.exception.EquipeCRUDException;
import com.francefootball.niceequipefootballapi.metier.exception.ResourceNotFoundException;
import com.francefootball.niceequipefootballapi.metier.service.interfaces.EquipeService;
import com.francefootball.niceequipefootballapi.persistence.model.Equipe;
import com.francefootball.niceequipefootballapi.persistence.model.Joueur;
import com.francefootball.niceequipefootballapi.persistence.repository.EquipeRepository;
import com.francefootball.niceequipefootballapi.persistence.repository.JoueurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.francefootball.niceequipefootballapi.metier.constants.ErrorMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class EquipeServiceImpl implements EquipeService {

    private final EquipeRepository equipeRepository;
    private final JoueurRepository joueurRepository;

    @Override
    public Page<EquipeDto> getAllEquipes(int page, int size, String sortBy, String sortDirection) {
        log.debug("Récupération de la liste des équipes avec pagination et tri");

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Equipe> equipePage = equipeRepository.findAll(pageable);
        if (equipePage.isEmpty()) {
            log.warn("La liste des équipes est vide");
            throw new ResourceNotFoundException(AUCUNE_EQUIPE_TROUVEE);
        }
        log.info("La liste des équipes est trouvée");
        return EquipeDto.of(equipePage);
    }

    @Override
    public Long enregistrerEquipe(EquipeDto equipeDto) {
        log.debug("Enregistrement de l'équipe : {}", equipeDto);
        this.verifierExistanceEquipe(equipeDto.id());
        log.info("debut de l'enregistrement de l'équipe");
        Equipe equipe = new Equipe();
        equipe.setNomEquipe(equipeDto.nom());
        equipe.setBudget(equipeDto.budget());
        equipe.setAcronym(equipeDto.acronym());

        this.ajouterJoueurs(equipe, equipeDto.joueursDtos());
        Equipe newEquipe = equipeRepository.save(equipe);
        return newEquipe.getIdEquipe();
    }

    private void verifierExistanceEquipe(Long idEquipe) {
        log.debug("Vérification de l'existence de l'équipe avec l'ID : {}", idEquipe);
        if (idEquipe != null && idEquipe > 0 && equipeRepository.existsById(idEquipe)) {
            log.warn("L'équipe avec l'ID {} existe déjà", idEquipe);
            throw new EquipeCRUDException(EQUIPE_EXISTE);
        }
    }

    private void ajouterJoueurs(Equipe equipe, List<JoueursDto> joueursDtos) {
        log.debug("Ajout des joueurs à l'équipe : {}", equipe.getNomEquipe());
        if (joueursDtos != null && !joueursDtos.isEmpty()) {
            joueursDtos.forEach(joueurDto -> {
                if (joueurDto.idJoueur() != null && joueurDto.idJoueur() > 0) {
                    this.verifierJoueurDansUneEquipe(joueurDto.idJoueur());
                }
                Joueur joueur = new Joueur();
                joueur.setNomJoueur(joueurDto.nomJoueur());
                joueur.setEquipe(equipe);
                PositionJoueur positionJoueur = PositionJoueur.fromLibelle(joueurDto.position());
                joueur.setPosition(positionJoueur);
                equipe.getJoueurs().add(joueur);
            });
        }
    }

    private void verifierJoueurDansUneEquipe(Long idJoueur) {
        log.debug("Vérification de l'existence du joueur avec l'ID : {} dans une autre equipe", idJoueur);
        Optional<Joueur> joueurExist = joueurRepository.findById(idJoueur);
        if (joueurExist.isPresent()) {
            if (joueurExist.get().getEquipe() != null) {
                log.warn("Le joueur avec l'ID {} existe déjà dans une autre équipe", idJoueur);
                throw new EquipeCRUDException(JOUEUR_A_EQUIPE);
            }
        }
    }

    @Override
    public EquipeDto getEquipeById(Long idEquipe) {
        log.info("Récupération de l'équipe avec l'ID : {}", idEquipe);
        Optional<Equipe> equipe = equipeRepository.findById(idEquipe);
        if (equipe.isPresent()) {
            log.debug("L'équipe avec l'ID {} a été trouvée, equipe {}", idEquipe, equipe);
            return EquipeDto.of(equipe.get());
        } else {
            log.warn("Aucune équipe trouvée avec l'ID {}", idEquipe);
            throw new ResourceNotFoundException(AUCUNE_EQUIPE_TROUVEE);
        }
    }
}
