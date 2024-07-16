package ets.schedule.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    
    @GetMapping("/api/v1/course")
    public String getAllCourses() {
        return "";
    }
}
