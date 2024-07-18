package ets.schedule.controllers;

import java.util.List;

import ets.schedule.data.responses.get.CourseGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ets.schedule.data.payloads.CoursePayload;
import ets.schedule.interfaces.services.CoursesService;

@RestController
public class CoursesController {

    @Autowired
    private CoursesService courseService;
    
    @GetMapping("/api/v1/course")
    public ResponseEntity<List<CourseGetResponse>> getAllCourses() {
        var response = courseService.getAll();
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/course")
    public ResponseEntity<CourseGetResponse> createCourse(@RequestBody CoursePayload payload) {
        var response = courseService.createCourse(payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PutMapping("/api/v1/course")
    public ResponseEntity<CourseGetResponse> editCourse(@RequestParam Long id, @RequestBody CoursePayload payload) {
        var response = courseService.editCourse(id, payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
