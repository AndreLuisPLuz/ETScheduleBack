package ets.schedule.controllers;

import java.util.List;

import ets.schedule.data.payloads.disciplines.DisciplinePayload;
import ets.schedule.data.responses.get.DisciplineGetResponse;
import ets.schedule.interfaces.services.DisciplinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DisciplinesController {

    @Autowired
    private DisciplinesService disciplinesService;

    @GetMapping("/api/v1/discipline")
    public ResponseEntity<List<DisciplineGetResponse>> getAllDisciplines() {
        var response = disciplinesService.getAllDisciplines();
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @GetMapping("/api/v1/discipline/{id}")
    public ResponseEntity<DisciplineGetResponse> getDisciplinesById(@PathVariable Long id) {
        var response = disciplinesService.getDisciplineById(id);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/discipline")
    public ResponseEntity<DisciplineGetResponse> createDiscipline(@RequestBody DisciplinePayload obj) {
        var response = disciplinesService.createDiscipline(obj);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
