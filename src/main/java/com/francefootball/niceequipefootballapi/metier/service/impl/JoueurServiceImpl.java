package com.francefootball.niceequipefootballapi.metier.service.impl;

import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;
import com.francefootball.niceequipefootballapi.metier.exception.ResourceNotFoundException;
import com.francefootball.niceequipefootballapi.metier.service.interfaces.JoueurService;
import com.francefootball.niceequipefootballapi.persistence.model.Joueur;
import com.francefootball.niceequipefootballapi.persistence.repository.JoueurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.francefootball.niceequipefootballapi.metier.constants.ErrorMessages.AUCUN_JOUEUR_TROUVE;

@Slf4j
@Service
@AllArgsConstructor
public class JoueurServiceImpl implements JoueurService {

    private final JoueurRepository joueurRepository;

    @Override
    public JoueursDto getDetailsJoueurById(Long idJoueur) {
        log.info("Récupération de Joueur avec l'ID : {}", idJoueur);
        Optional<Joueur> joueur = joueurRepository.findById(idJoueur);
        if (joueur.isPresent()) {
            log.debug("Le Joueur a été trouvée, equipe {}", joueur);
            return JoueursDto.of(joueur.get());
        } else {
            log.warn("Aucun Joueur trouvée avec l'ID {}", idJoueur);
            throw new ResourceNotFoundException(AUCUN_JOUEUR_TROUVE);
        }
    }
}
