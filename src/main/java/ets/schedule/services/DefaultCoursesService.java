package ets.schedule.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.courses.CoursePayload;
import ets.schedule.interfaces.services.CoursesService;
import ets.schedule.models.Courses;
import ets.schedule.repositories.CoursesRepository;

public class DefaultCoursesService implements CoursesService {

    @Autowired
    public CoursesRepository repo;

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

            var course = repo.findById(id);
//            if(course.isEmpty()) {
//                throw new NotFoundException("Course was not found.");
//            }

            if (payload.name() != null && !payload.name().isEmpty()) {
                course.get().setName(payload.name());
            }

            if (payload.description() != null && !payload.description().isEmpty()) {
                course.get().setDescription(payload.description());
            }

            return new HttpEntity<>(
                HttpStatusCode.valueOf(201),
                    repo.save(course.get())
            );
        });
    }
    
}
