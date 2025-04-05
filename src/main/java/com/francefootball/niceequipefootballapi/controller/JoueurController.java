package com.francefootball.niceequipefootballapi.controller;

import com.francefootball.niceequipefootballapi.metier.dto.JoueursDto;
import com.francefootball.niceequipefootballapi.metier.service.interfaces.JoueurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_JOUEUR;

@RestController
@AllArgsConstructor
@RequestMapping(PATH_JOUEUR)
public class JoueurController {

    final private JoueurService joueurService;

    @GetMapping("/{idJoueur}")
    public ResponseEntity<JoueursDto> getDetailsJoueurById(@PathVariable("idJoueur") Long idJoueur) {
        return ResponseEntity.ok(joueurService.getDetailsJoueurById(idJoueur));
    }

}


