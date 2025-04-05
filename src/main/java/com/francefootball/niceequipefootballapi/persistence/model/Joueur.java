package com.francefootball.niceequipefootballapi.persistence.model;

import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "joueur")
@Getter
@Setter
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_joueur", nullable = false)
    private Long idJoueur;

    @Column(name = "nom_joueur", nullable = false)
    private String nomJoueur;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private PositionJoueur position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

}
