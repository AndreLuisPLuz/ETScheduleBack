package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.StudentAvaliationPayload;
import ets.schedule.data.responses.get.StudentAvaliationGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.StudentAvaliationService;
import ets.schedule.models.StudentAvaliation;
import ets.schedule.repositories.DisciplinesJPARepository;
import ets.schedule.repositories.ProfilesJPARepository;
import ets.schedule.repositories.StudentAvaliationJPARepository;
import ets.schedule.sessions.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class DefaultStudentAvaliationService implements StudentAvaliationService {

    @Autowired
    private StudentAvaliationJPARepository studentAvaliationRepository;

    @Autowired
    private ProfilesJPARepository profilesRepository;

    @Autowired
    private DisciplinesJPARepository disciplinesRepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<StudentAvaliationGetResponse> getAvaliationByDiscipline(Long studentProfileId, Long disciplineId) {
        if(userSession.getProfileRole() == ProfileRole.Student
            && !studentProfileId.equals(userSession.getProfileId()))
        {
            throw new ApplicationException(403, "User does not have permission to view this avaliation.");
        }

        var profile = profilesRepository.findById(studentProfileId)
                .orElseThrow(() -> new ApplicationException(403, "Profile not found."));

        return new HttpList<>(
                HttpStatus.valueOf(200),
                studentAvaliationRepository
                        .findByDisciplineIdAndStudent_Id(studentProfileId, disciplineId)
                        .stream()
                        .map(StudentAvaliationGetResponse::buildFromEntity)
                        .toList()
        );
    }

    @Override
    public HttpEntity<StudentAvaliationGetResponse> registerAvaliation(StudentAvaliationPayload payload) {
        if(userSession.getProfileRole() == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to register this avaliation.");
        }

        var discipline = disciplinesRepository.findById(payload.disciplineId())
                .orElseThrow(() -> new ApplicationException(403, "Discipline not found."));

        var student = profilesRepository.findById(payload.studentId())
                .orElseThrow(() -> new ApplicationException(403, "Student not found."));

        var studentAvaliation = StudentAvaliation.build(discipline, student, payload.comment());
        studentAvaliationRepository.save(studentAvaliation);

        return new HttpEntity<>(
                HttpStatus.valueOf(200),
                StudentAvaliationGetResponse.buildFromEntity(studentAvaliation)
        );
    }

    @Override
    public HttpEntity<StudentAvaliationGetResponse> updateAvaliation(Long studentAvaliationId, StudentAvaliationPayload payload) {
        return null;
    }
}
