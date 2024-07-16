package ets.schedule.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.models.Courses;
import ets.schedule.data.payloads.CourseCreatePayload;
import ets.schedule.interfaces.services.CourseService;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;
    
    @GetMapping("/api/v1/course")
    public ResponseEntity<List<Courses>> getAllCourses() {
        try {
            var response = courseService.getAll();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @PostMapping("/api/v1/course")
    public ResponseEntity<List<Courses>> createCourse(@RequestBody CourseCreatePayload payload) {
        try {
            var response = courseService.createCourse(payload);
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }
}
