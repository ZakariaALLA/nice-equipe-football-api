package com.francefootball.niceequipefootballapi.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipe")
@Getter
@Setter
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe", nullable = false)
    private Long idEquipe;

    @Column(name = "nom_equipe", nullable = false)
    private String nomEquipe;

    @Column(name = "acronym", nullable = false)
    private String acronym;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    private List<Joueur> joueurs = new ArrayList<>();

}
