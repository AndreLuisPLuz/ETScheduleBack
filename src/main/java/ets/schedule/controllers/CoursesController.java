package ets.schedule.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.models.Courses;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CoursePayload;
import ets.schedule.interfaces.services.CoursesService;

@RestController
public class CoursesController {

    @Autowired
    private CoursesService courseService;
    
    @GetMapping("/api/v1/course")
    public ResponseEntity<List<Courses>> getAllCourses() {
        try {
            var response = courseService.getAll().get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @PostMapping("/api/v1/course")
    public ResponseEntity<Courses> createCourse(@RequestBody CoursePayload payload) {
        try {
            var response = courseService.createCourse(payload).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @PutMapping("/api/v1/course")
    public ResponseEntity<Courses> editCourse(@RequestParam Long id, @RequestBody CoursePayload payload) {
        try {
            var response = courseService.editCourse(id, payload).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }
}
