package ets.schedule.controllers;

import ets.schedule.data.payloads.CompetencePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;
import ets.schedule.interfaces.services.CompetencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompetencesController {

    @Autowired
    private CompetencesService competencesService;

    @GetMapping("api/v1/competence")
    public ResponseEntity<List<CompetenceGetResponse>> getCompetences(@RequestParam Long disciplineId) {
        var response = competencesService.getAllCompetencesByDiscipline(disciplineId);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("api/v1/competence")
    public ResponseEntity<CompetenceGetResponse> createCompetence(@RequestParam Long disciplineId ,@RequestBody CompetencePayload payload) {
        var response = competencesService.createCompetence(disciplineId, payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
