package ets.schedule.controllers;

import org.springframework.web.bind.annotation.RestController;

import ets.schedule.data.responses.instructor.InstructorResponse;
import ets.schedule.interfaces.services.InstructorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class InstructorController {
    @Autowired
    InstructorService service;

    @GetMapping("/api/v1/instructor")
    public ResponseEntity<List<InstructorResponse>> getAllInstructors() {
        var response = service.getAllInstructors();
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
