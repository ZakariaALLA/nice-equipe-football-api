package com.francefootball.niceequipefootballapi.metier.enums;

import com.francefootball.niceequipefootballapi.metier.exception.PositionInvalideException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.francefootball.niceequipefootballapi.metier.constants.ErrorMessages.POSITION_INVALIDE;

@Getter
public enum PositionJoueur {

    GARDIEN("Gardien"),

    ARRIERE_DROIT("Arrière Droit"),
    ARRIERE_GAUCHE("Arrière Gauche"),
    DEFENSEUR_CENTRAL("Défenseur Central"),
    LIBERO("Libéro"),

    MILIEU_DEFENSIF("Milieu Défensif"),
    MILIEU_CENTRAL("Milieu Central"),
    MILIEU_OFFENSIF("Milieu Offensif"),

    AILIER_DROIT("Ailier Droit"),
    AILIER_GAUCHE("Ailier Gauche"),

    SECOND_ATTAQUANT("Second Attaquant"),
    ATTAQUANT("Attaquant"),
    AVANT_CENTRAL("Avant-Central");

    private final String libelle;

    PositionJoueur(String libelle) {
        this.libelle = libelle;
    }

    public static PositionJoueur fromLibelle(String libelle) {
        for (PositionJoueur position : PositionJoueur.values()) {
            if (position.getLibelle().equalsIgnoreCase(libelle)) {
                return position;
            }
        }
        throw new PositionInvalideException(POSITION_INVALIDE);
    }

    public static List<String> getAllLibelle() {
        return Arrays.stream(PositionJoueur.values())
                .map(positionJoueur -> positionJoueur.libelle)
                .toList();
    }

}
