package com.francefootball.niceequipefootballapi.metier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import com.francefootball.niceequipefootballapi.persistence.model.Equipe;
import com.francefootball.niceequipefootballapi.persistence.model.Joueur;

import java.util.List;

public record JoueursDto(
        @JsonProperty("id_joueur")
        Long idJoueur,
        @JsonProperty("nom_joueur")
        String nomJoueur,
        @JsonProperty("position")
        String position
) {
    public static JoueursDto of(Joueur joueur) {
        return new JoueursDto(
                joueur.getIdJoueur(),
                joueur.getNomJoueur(),
                joueur.getPosition().getLibelle()
        );
    }
    public static List<JoueursDto> of(List<Joueur> joueurList) {
        return joueurList.stream().map(JoueursDto::of).toList();
    }
}
