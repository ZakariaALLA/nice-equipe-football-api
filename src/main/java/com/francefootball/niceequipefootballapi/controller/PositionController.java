package com.francefootball.niceequipefootballapi.controller;

import com.francefootball.niceequipefootballapi.metier.enums.PositionJoueur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_POSITION;

@RestController
@RequestMapping(PATH_POSITION)
public class PositionController {

    @GetMapping
    public ResponseEntity<List<String>> getAllPositionJoueur() {
        return ResponseEntity.ok(PositionJoueur.getAllLibelle());
    }

}
