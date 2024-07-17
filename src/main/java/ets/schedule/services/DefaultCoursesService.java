package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.responses.get.CourseGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.courses.CoursePayload;
import ets.schedule.interfaces.services.CoursesService;
import ets.schedule.models.Courses;
import ets.schedule.repositories.CoursesJPARepository;

public class DefaultCoursesService implements CoursesService {

    @Autowired
    public CoursesJPARepository repo;

    @Override
    public HttpList<CourseGetResponse> getAll() {
        var courses = repo.findAll().stream().map(
                CourseGetResponse::buildFromEntity
        );

        return new HttpList<CourseGetResponse>(
            HttpStatusCode.valueOf(200),
            courses.toList()
        );
    }

    @Override
    public HttpEntity<CourseGetResponse> createCourse(CoursePayload payload) {
        Courses newCourse = new Courses(payload.name(), payload.description());
        repo.save(newCourse);

        return new HttpEntity<>(
            HttpStatusCode.valueOf(201),
            CourseGetResponse.buildFromEntity(newCourse)
        );
    }

    @Override
    public HttpEntity<CourseGetResponse> editCourse(Long id, CoursePayload payload) {
        var opCourse = repo.findById(id);
        if(opCourse.isEmpty()) {
            throw new ApplicationException(404, "Course could not be found.");
        }

        if (payload.name() != null && !payload.name().isEmpty()) {
            opCourse.get().setName(payload.name());
        }

        if (payload.description() != null && !payload.description().isEmpty()) {
            opCourse.get().setDescription(payload.description());
        }

        var course = repo.save(opCourse.get());

        return new HttpEntity<>(
            HttpStatusCode.valueOf(201),
                CourseGetResponse.buildFromEntity(course)
        );
    }
    
}
