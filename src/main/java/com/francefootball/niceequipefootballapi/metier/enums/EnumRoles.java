package com.francefootball.niceequipefootballapi.metier.enums;

import com.francefootball.niceequipefootballapi.metier.exception.RoleInvalideException;
import lombok.Getter;

import static com.francefootball.niceequipefootballapi.metier.constants.ErrorMessages.ROLE_INVALIDE;

@Getter
public enum EnumRoles {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String libelle;

    EnumRoles(String libelle) {
        this.libelle = libelle;
    }

    public static EnumRoles fromLibelle(String libelle) {
        for (EnumRoles role : EnumRoles.values()) {
            if (role.getLibelle().equalsIgnoreCase(libelle)) {
                return role;
            }
        }
        throw new RoleInvalideException(ROLE_INVALIDE);
    }
}

