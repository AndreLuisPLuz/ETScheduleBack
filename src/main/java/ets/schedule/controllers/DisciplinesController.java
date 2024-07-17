package ets.schedule.controllers;

import java.util.List;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.disciplines.DisciplinePayload;
import ets.schedule.interfaces.services.DisciplinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ets.schedule.models.Disciplines;

@RestController
public class DisciplinesController {

    @Autowired
    private DisciplinesService disciplinesService;

    @GetMapping("/api/v1/discipline")
    public ResponseEntity<List<Disciplines>> getAllDisciplines() {
        try {
            var response = disciplinesService.getAllDisciplines().get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @GetMapping("/api/v1/discipline/{id}")
    public ResponseEntity<Disciplines> getDisciplinesById(@PathVariable Long id) {
        try {
            var response = disciplinesService.getDisciplineById(id).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @PostMapping("/api/v1/discipline")
    public ResponseEntity<Disciplines> createDiscipline(@RequestBody DisciplinePayload obj) {
        try {
            var response = disciplinesService.createDiscipline(obj).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }
}
