package com.francefootball.niceequipefootballapi.metier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.francefootball.niceequipefootballapi.persistence.model.Equipe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public record EquipeDto(
        @JsonProperty("id_equipe")
        Long id,

        @JsonProperty("nom_equipe")
        @NotBlank(message = "Le nom de l'équipe ne doit pas être vide")
        String nom,

        @JsonProperty("acronym")
        @NotBlank(message = "L'acronym de l'équipe ne doit pas être vide")
        String acronym,

        @JsonProperty("budget")
        @NotNull(message = "Le budget de l'équipe est obligatoire")
        @Min(value = 1, message = "Le budget de l'équipe doit etre strictement superieur à 0")
        BigDecimal budget,

        @JsonProperty("joueurs")
        List<JoueursDto> joueursDtos
) {

    public static EquipeDto of(Equipe equipe) {
        return new EquipeDto(
                equipe.getIdEquipe(),
                equipe.getNomEquipe(),
                equipe.getAcronym(),
                equipe.getBudget(),
                JoueursDto.of(equipe.getJoueurs())
        );
    }

    public static Page<EquipeDto> of(Page<Equipe> equipePage) {
        return equipePage.map(EquipeDto::of);

    }
}
