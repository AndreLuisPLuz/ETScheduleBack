package ets.schedule.controllers;

import ets.schedule.data.payloads.StudentCompetencePayload;
import ets.schedule.data.responses.get.StudentAvaliationGetResponse;
import ets.schedule.data.responses.get.StudentCompetencesGetResponse;
import ets.schedule.interfaces.services.StudentCompetencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentCompetencesController {

    @Autowired
    private StudentCompetencesService studentCompetencesService;

    @GetMapping("api/v1/student/competence")
    public ResponseEntity<List<StudentCompetencesGetResponse>> getStudentCompetences(
            @RequestParam Long disciplineId,
            @RequestParam Long studentId
    ){
        var response = studentCompetencesService.getStudentCompetences(disciplineId, studentId);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("api/v1/student/competence")
    public ResponseEntity<StudentCompetencesGetResponse> registerStudentCompetence(
            @RequestBody StudentCompetencePayload payload
            ){
        var response = studentCompetencesService.registerStudentCompetence(payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PutMapping("api/v1/student/competence")
    public ResponseEntity<StudentCompetencesGetResponse> updateStudentCompetence(
            @RequestParam Long competenceId,
            @RequestBody StudentCompetencePayload payload
    ){
        var response = studentCompetencesService.updateStudentCompetence(competenceId, payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
