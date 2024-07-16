package ets.schedule.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.CourseCreatePayload;
import ets.schedule.data.responses.UserResponse;
import ets.schedule.interfaces.services.CourseService;
import ets.schedule.models.Courses;
import ets.schedule.models.Users;
import ets.schedule.repositories.CourseRepository;

public class DefaultCourseService implements CourseService {

    @Autowired
    public CourseRepository repo;

    @Override
    public CompletableFuture<HttpEntity<List<Courses>>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            return new HttpEntity<>(
                HttpStatusCode.valueOf(200),
                repo.findAll()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Courses>> createCourse(CourseCreatePayload payload) {
        return CompletableFuture.supplyAsync(() -> {

            Courses newCourse = new Courses(payload.name(), payload.description());
            repo.save(newCourse);

            return new HttpEntity<>(
                HttpStatusCode.valueOf(200),
                newCourse
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Courses>> editCourse(Long id) {
        return CompletableFuture.supplyAsync(() -> {

            var course = repo.findById(id).orElseThrow(null)

            return new HttpEntity<>(
                HttpStatusCode.valueOf(200),
                newCourse
            );
        });
    }
    
}
