package ets.schedule.controllers;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.StudentAvaliationPayload;
import ets.schedule.data.payloads.StudentCompetencePayload;
import ets.schedule.data.responses.get.StudentAvaliationGetResponse;
import ets.schedule.interfaces.services.StudentAvaliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentAvaliationController {

    @Autowired
    private StudentAvaliationService studentAvaliationService;

    @GetMapping("api/v1/avaliation")
    public ResponseEntity<List<StudentAvaliationGetResponse>> getStudentAvaliation(
            @RequestParam Long studentId,
            @RequestParam Long disciplineId
    ) {
        var response = studentAvaliationService.getAvaliationByDiscipline(studentId, disciplineId);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("api/v1/avaliation")
    public ResponseEntity<StudentAvaliationGetResponse> getStudentAvaliation(
            @RequestBody StudentAvaliationPayload payload
            ) {
        var response = studentAvaliationService.registerAvaliation(payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PutMapping("api/v1/avaliation")
    public ResponseEntity<StudentAvaliationGetResponse> editStudentAvaliation(
            @RequestParam Long studentAvaliationId,
            @RequestBody StudentAvaliationPayload payload
    ) {
        var response = studentAvaliationService.updateAvaliation(studentAvaliationId, payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
