package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.disciplines.DisciplinePayload;
import ets.schedule.data.responses.get.DisciplineGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.DisciplinesService;
import ets.schedule.models.Disciplines;
import ets.schedule.repositories.*;

import ets.schedule.sessions.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
<<<<<<< HEAD

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
=======
>>>>>>> 85324aff2e3ebade9e303e3c1d809a77f8dbe0f3

public class DefaultDisciplinesService implements DisciplinesService {

    @Autowired
    private DisciplinesJPARepository disciplinesJPARepository;

    @Autowired
    private CoursesJPARepository coursesJPARepository;

    @Autowired
    private GroupsJPARepository groupsJPARepository;

    @Autowired
    private ProfilesJPARepository profilesJPARepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<DisciplineGetResponse> getAllDisciplines() {
        List<DisciplineGetResponse> disciplines = null;

        if(userSession.getProfileRole() == ProfileRole.Admin) {
            disciplines = disciplinesJPARepository.findAll().stream().map(
                    DisciplineGetResponse::buildFromEntity
            ).toList();
        }

        if(userSession.getProfileRole() == ProfileRole.Instructor) {
            disciplines = disciplinesJPARepository.findDisciplinesByInstructor_Id(
                    userSession.getUserId())
                    .stream()
                    .map(DisciplineGetResponse::buildFromEntity)
                    .toList();
        }

        return new HttpList<DisciplineGetResponse>(
                HttpStatusCode.valueOf(200),
                disciplines
        );
    }

    @Override
    public HttpEntity<DisciplineGetResponse> getDisciplineById(Long id) {
        Optional<Disciplines> discipline = null;

        if(userSession.getProfileRole() == ProfileRole.Admin) {
            discipline = disciplinesJPARepository.findById(id);
        }

//        if(userSession.getProfileRole() == ProfileRole.Instructor) {
//            var
//        }

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
        var instructor = profilesJPARepository.findById(obj.instructorId());

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
