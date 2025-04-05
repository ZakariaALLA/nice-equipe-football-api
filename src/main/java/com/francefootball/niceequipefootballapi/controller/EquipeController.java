package com.francefootball.niceequipefootballapi.controller;

import com.francefootball.niceequipefootballapi.config.annotations.ValidateSortDirection;
import com.francefootball.niceequipefootballapi.config.annotations.ValidateSortedByField;
import com.francefootball.niceequipefootballapi.metier.dto.EquipeDto;
import com.francefootball.niceequipefootballapi.metier.service.interfaces.EquipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_EQUIPE;

@RestController
@RequestMapping(PATH_EQUIPE)
@AllArgsConstructor
@Validated
public class EquipeController {

    private final EquipeService equipeService;

    @GetMapping
    public ResponseEntity<Page<EquipeDto>> getAllEquipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomEquipe")
            @ValidateSortedByField
            String sortBy,
            @RequestParam(defaultValue = "ASC")
            @ValidateSortDirection
            String sortDirection) {

        return ResponseEntity.ok(
                equipeService.getAllEquipes(page, size, sortBy, sortDirection)
        );
    }

    @GetMapping("/{idEquipe}")
    public ResponseEntity<EquipeDto> getEquipesById(@PathVariable("idEquipe") Long idEquipe) {
        return ResponseEntity.ok(equipeService.getEquipeById(idEquipe));
    }

    @PostMapping
    public ResponseEntity<Long> enregistrerEquipe(@Valid @RequestBody EquipeDto equipeDto) {
        return new ResponseEntity<>(equipeService.enregistrerEquipe(equipeDto), HttpStatus.CREATED);
    }
}
