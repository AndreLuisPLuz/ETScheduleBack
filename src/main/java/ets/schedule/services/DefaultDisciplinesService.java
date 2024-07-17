package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.disciplines.DisciplinePayload;
import ets.schedule.data.responses.get.DisciplineGetResponse;
import ets.schedule.interfaces.services.DisciplinesService;
import ets.schedule.models.Disciplines;
import ets.schedule.repositories.CoursesJPARepository;
import ets.schedule.repositories.DisciplinesJPARepository;
import ets.schedule.repositories.GroupsJPARepository;
import ets.schedule.repositories.UserJPARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import java.util.concurrent.CompletableFuture;

public class DefaultDisciplinesService implements DisciplinesService {

    @Autowired
    private DisciplinesJPARepository disciplinesJPARepository;

    @Autowired
    private CoursesJPARepository coursesJPARepository;

    @Autowired
    private GroupsJPARepository groupsJPARepository;

    @Autowired
    private UserJPARepository userRepository;

    @Override
    public HttpList<DisciplineGetResponse> getAllDisciplines() {
        var disciplines = disciplinesJPARepository.findAll().stream().map(
                DisciplineGetResponse::buildFromEntity
        );

        return new HttpList<DisciplineGetResponse>(
                HttpStatusCode.valueOf(200),
                disciplines.toList()
        );
    }

    @Override
    public HttpEntity<DisciplineGetResponse> getDisciplineById(Long id) {
        var discipline = disciplinesJPARepository.findById(id);
        if (discipline.isEmpty()) {
            throw new ApplicationException(404, "Discipline could not be found.");
        }

        return new HttpEntity<DisciplineGetResponse>(
                HttpStatusCode.valueOf(200),
                DisciplineGetResponse.buildFromEntity(discipline.get())
        );
    }

    @Override
    public HttpEntity<DisciplineGetResponse> createDiscipline(DisciplinePayload obj) {
        var course = coursesJPARepository.findById(obj.courseId());
        var group = groupsJPARepository.findById(obj.groupId());
        var instructor = userRepository.findById(obj.instructorId());

        if(course.isEmpty()) {
            throw new ApplicationException(404, "Course could not be found.");
        } else if(group.isEmpty()) {
            throw new ApplicationException(404, "Group could not be found.");
        } else if(instructor.isEmpty()) {
            throw new ApplicationException(404, "Instructor could not be found.");
        }

        var newDiscipline = new Disciplines(obj.semester());
        newDiscipline.setCourse(course.get());
        newDiscipline.setGroup(group.get());
        newDiscipline.setInstructor(instructor.get());

        disciplinesJPARepository.save(newDiscipline);

        return new HttpEntity<DisciplineGetResponse>(
                HttpStatusCode.valueOf(201),
                DisciplineGetResponse.buildFromEntity(newDiscipline)
        );

    }
}
