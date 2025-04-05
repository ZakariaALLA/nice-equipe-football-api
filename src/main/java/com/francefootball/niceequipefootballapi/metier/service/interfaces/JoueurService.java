package com.francefootball.niceequipefootballapi.metier.service.interfaces;

import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;

public interface JoueurService {
    JoueursDto getDetailsJoueurById(Long idJoueur);
}
