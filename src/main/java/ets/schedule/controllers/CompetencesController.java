package ets.schedule.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ets.schedule.data.payloads.competence.CompetencePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;
import ets.schedule.interfaces.services.CompetencesService;


@RestController
public class CompetencesController {
    
    @Autowired
    private CompetencesService competenceService;

    @GetMapping("/api/v1/competence")
    public ResponseEntity<List<CompetenceGetResponse>> getAllCompetences() {
        var response = competenceService.getAll();
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/competence")
    public ResponseEntity<CompetenceGetResponse> createCompetences(@RequestBody CompetencePayload payload) {
        var response = competenceService.createCompetence(payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PutMapping("/api/v1/competence")
    public ResponseEntity<CompetenceGetResponse> editCompetences(@RequestParam Long id, @RequestBody CompetencePayload payload) {
        var response = competenceService.editCompetence(id, payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
