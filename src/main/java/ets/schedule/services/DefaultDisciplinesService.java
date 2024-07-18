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
import java.util.List;

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
        return null;
    }

//    @Override
//    public HttpEntity<DisciplineGetResponse> getDisciplineById(Long id) {
//        Optional<Disciplines> discipline = null;
//
//        if(userSession.getProfileRole() == ProfileRole.Admin) {
//            discipline = disciplinesJPARepository.findById(id);
//        }
//
////        if(userSession.getProfileRole() == ProfileRole.Instructor) {
////            var
////        }
//
//        if (discipline.isEmpty()) {
//            throw new ApplicationException(404, "Discipline could not be found.");
//        }
//
//        return new HttpEntity<DisciplineGetResponse>(
//                HttpStatusCode.valueOf(200),
//                DisciplineGetResponse.buildFromEntity(discipline.get())
//        );
//    }

    @Override
    public HttpEntity<DisciplineGetResponse> createDiscipline(DisciplinePayload obj) {
        var course = coursesJPARepository.findById(obj.courseId())
                .orElseThrow(() -> new ApplicationException(404, "Course could not be found."));

        var group = groupsJPARepository.findById(obj.groupId())
                .orElseThrow(() -> new ApplicationException(404, "Group could not be found."));

        var instructor = profilesJPARepository.findById(obj.instructorId())
                .orElseThrow(() -> new ApplicationException(404, "Instructor could not be found."));

        var newDiscipline = Disciplines.build(group, instructor, course, obj.semester());
        disciplinesJPARepository.save(newDiscipline);

        return new HttpEntity<DisciplineGetResponse>(
                HttpStatusCode.valueOf(201),
                DisciplineGetResponse.buildFromEntity(newDiscipline)
        );

    }
}
