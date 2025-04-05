package com.francefootball.niceequipefootballapi.metier.service.interfaces;

import com.francefootball.niceequipefootballapi.metier.dto.EquipeDto;
import org.springframework.data.domain.Page;

public interface EquipeService {
    Page<EquipeDto> getAllEquipes(int page, int size, String sortBy, String sortDirection);

    Long enregistrerEquipe(EquipeDto equipeDto);

    EquipeDto getEquipeById(Long idEquipe);

}
