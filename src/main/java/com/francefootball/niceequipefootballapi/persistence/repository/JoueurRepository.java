package com.francefootball.niceequipefootballapi.persistence.repository;

import com.francefootball.niceequipefootballapi.persistence.model.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {

}
