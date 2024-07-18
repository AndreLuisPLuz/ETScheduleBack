package ets.schedule.controllers;

import org.springframework.web.bind.annotation.RestController;

import ets.schedule.data.responses.report.ReportResponse;
import ets.schedule.interfaces.services.StudentReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class StudentReportController {
    @Autowired
    StudentReportService service;

    @GetMapping("/api/v1/report/profile/{id}")
    public ResponseEntity<ReportResponse> getStudentResponse(
            @PathVariable Long profileId
    ) {
        var response = service.fetchStudentReport(profileId);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
