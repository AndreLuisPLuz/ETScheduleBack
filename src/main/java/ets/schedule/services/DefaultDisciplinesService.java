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

            return new HttpList<DisciplineGetResponse>(
                    HttpStatusCode.valueOf(200),
                    disciplines
            );
        }

        var profile = profilesJPARepository.findById(userSession.getProfileId())
                .orElseThrow(() -> new ApplicationException(403, "User profile could not be found."));

        if(userSession.getProfileRole() == ProfileRole.Instructor) {
            disciplines = profile.getDisciplines()
                    .stream()
                    .map(DisciplineGetResponse::buildFromEntity)
                    .toList();
        }

        if(userSession.getProfileRole() == ProfileRole.Student) {
            var group = groupsJPARepository.findById(profile.getGroup().getId())
                    .orElseThrow(() -> new ApplicationException(403, "Group could not be found."));

            disciplines = group.getDisciplines()
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
        var discipline = disciplinesJPARepository.findById(id)
                .orElseThrow(() -> new ApplicationException(403, "Discipline could not be found."));

        if(userSession.getProfileRole() == ProfileRole.Admin) {

            return new HttpEntity<DisciplineGetResponse>(
                    HttpStatusCode.valueOf(200),
                    DisciplineGetResponse.buildFromEntity(discipline)
            );
        }

        var profile = profilesJPARepository.findById(userSession.getProfileId())
                .orElseThrow(() -> new ApplicationException(403, "User profile could not be found."));

        if(userSession.getProfileRole() == ProfileRole.Instructor) {
            if (!profile.getDisciplines().contains(discipline)) {
                throw new ApplicationException(403, "User does not have permission to access this discipline.");
            }
        }

        if(userSession.getProfileRole() == ProfileRole.Student) {
            var group = groupsJPARepository.findById(profile.getGroup().getId())
                    .orElseThrow(() -> new ApplicationException(403, "Group could not be found."));

            if (!group.getDisciplines().contains(discipline)) {
                throw new ApplicationException(403, "User does not have permission to access this discipline.");
            }
        }

        return new HttpEntity<DisciplineGetResponse>(
                HttpStatusCode.valueOf(200),
                DisciplineGetResponse.buildFromEntity(discipline)
        );
    }

    @Override
    public HttpEntity<DisciplineGetResponse> createDiscipline(DisciplinePayload obj) {

        if(userSession.getProfileRole() != ProfileRole.Admin) {
            throw new ApplicationException(403, "User does not have permission to create disciplines.");
        }

        var course = coursesJPARepository.findById(obj.courseId())
                .orElseThrow(() -> new ApplicationException(403, "Course could not be found."));

        var group = groupsJPARepository.findById(obj.groupId())
                .orElseThrow(() -> new ApplicationException(403, "Group could not be found."));

        var instructor = profilesJPARepository.findById(obj.instructorId())
                .orElseThrow(() -> new ApplicationException(403, "Instructor could not be found."));

        var newDiscipline = new Disciplines(obj.semester());
        newDiscipline.setCourse(course);
        newDiscipline.setGroup(group);
        newDiscipline.setInstructor(instructor);

        disciplinesJPARepository.save(newDiscipline);

        return new HttpEntity<DisciplineGetResponse>(
                HttpStatusCode.valueOf(201),
                DisciplineGetResponse.buildFromEntity(newDiscipline)
        );

    }
}
