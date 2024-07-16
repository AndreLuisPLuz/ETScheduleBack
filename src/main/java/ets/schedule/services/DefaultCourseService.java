package ets.schedule.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CoursePayload;
import ets.schedule.data.responses.UserResponse;
import ets.schedule.interfaces.services.CourseService;
import ets.schedule.models.Courses;
import ets.schedule.models.Users;
import ets.schedule.repositories.CourseRepository;

public class DefaultCourseService implements CourseService {

    @Autowired
    public CourseRepository repo;

    @Override
    public CompletableFuture<HttpList<Courses>> getAll() {
        return CompletableFuture.supplyAsync(() -> {

            return new HttpList<Courses>(
                HttpStatusCode.valueOf(200),
                repo.findAll()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Courses>> createCourse(CoursePayload payload) {
        return CompletableFuture.supplyAsync(() -> {

            Courses newCourse = new Courses(payload.name(), payload.description());
            Courses savedCourse = repo.save(newCourse);

            return new HttpEntity<>(
                HttpStatusCode.valueOf(201),
                savedCourse
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Courses>> editCourse(Long id, CoursePayload payload) {
        return CompletableFuture.supplyAsync(() -> {

            var course = (Courses) repo.findById(id).get();

            if (payload.name() != null && !payload.name().isEmpty()) {
                course.setName(payload.name());
            }

            if (payload.description() != null && !payload.description().isEmpty()) {
                course.setDescription(payload.description());
            }

            repo.save(course);

            return new HttpEntity<>(
                HttpStatusCode.valueOf(200),
                course
            );
        });
    }
    
}
