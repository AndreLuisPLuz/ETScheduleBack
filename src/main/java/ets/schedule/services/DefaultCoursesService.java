package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.responses.get.CourseGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.sessions.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CoursePayload;
import ets.schedule.interfaces.services.CoursesService;
import ets.schedule.models.Courses;
import ets.schedule.repositories.CoursesJPARepository;

public class DefaultCoursesService implements CoursesService {

    @Autowired
    private CoursesJPARepository courseRepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<CourseGetResponse> getAll() {
        var courses = courseRepository.findAll()
                .stream()
                .map(CourseGetResponse::buildFromEntity)
                .toList();

        return new HttpList<CourseGetResponse>(
            HttpStatusCode.valueOf(200),
            courses
        );
    }

    @Override
    public HttpEntity<CourseGetResponse> createCourse(CoursePayload payload) {
        if(userSession.getProfileRole() != ProfileRole.Admin) {
            throw new ApplicationException(403, "User privilege level required not attended.");
        }
        var newCourse = Courses.build(payload.name(), payload.description());
        courseRepository.save(newCourse);

        return new HttpEntity<>(
            HttpStatusCode.valueOf(201),
            CourseGetResponse.buildFromEntity(newCourse)
        );
    }

    @Override
    public HttpEntity<CourseGetResponse> editCourse(Long id, CoursePayload payload) {
        if(userSession.getProfileRole() != ProfileRole.Admin) {
            throw new ApplicationException(403, "User privilege level required not attended.");
        }

        var course = courseRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(404, "Course not found."));

        if (payload.name() != null && !payload.name().isEmpty()) {
            course.setName(payload.name());
        }

        if (payload.description() != null && !payload.description().isEmpty()) {
            course.setDescription(payload.description());
        }

        courseRepository.save(course);

        return new HttpEntity<>(
            HttpStatusCode.valueOf(201),
                CourseGetResponse.buildFromEntity(course)
        );
    }
    
}
