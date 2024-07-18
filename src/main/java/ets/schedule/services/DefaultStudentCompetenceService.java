package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CompetencePayload;
import ets.schedule.data.payloads.StudentCompetencePayload;
import ets.schedule.data.responses.get.StudentCompetencesGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.StudentCompetencesService;
import ets.schedule.models.StudentCompetences;
import ets.schedule.repositories.CompetencesJPARepository;
import ets.schedule.repositories.DisciplinesJPARepository;
import ets.schedule.repositories.ProfilesJPARepository;
import ets.schedule.repositories.StudentCompetencesJPARepository;
import ets.schedule.sessions.UserSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Objects;

public class DefaultStudentCompetenceService implements StudentCompetencesService {

    @Autowired
    private StudentCompetencesJPARepository studentCompetencesRepository;

    @Autowired
    private ProfilesJPARepository profilesRepository;

    @Autowired
    private CompetencesJPARepository competencesRepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<StudentCompetencesGetResponse> getStudentCompetences(Long disciplineId, Long studentId) {
        if(userSession.getProfileRole() == ProfileRole.Student
            && !Objects.equals(studentId, userSession.getUserId())) {
                throw new ApplicationException(403, "User does not have permission to access this competence.");
        }

        var profile = profilesRepository.findById(studentId)
                .orElseThrow(() -> new ApplicationException(404, "Student not found."));

        return new HttpList<>(
                HttpStatus.valueOf(200),
                profile.getStudentCompetences()
                        .stream()
                        .map(StudentCompetencesGetResponse::buildFromEntity)
                        .toList()
                );
    }

    @Override
    public HttpEntity<StudentCompetencesGetResponse> registerStudentCompetence(StudentCompetencePayload payload) {
        if(userSession.getProfileRole() == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to register competences.");
        }

        var student = profilesRepository.findById(payload.studentId())
                .orElseThrow(() -> new ApplicationException(404, "Student not found."));

        var competence = competencesRepository.findById(payload.competenceId())
                .orElseThrow(() -> new ApplicationException(404, "Competence not found."));

        var studentCompetence = StudentCompetences.build(competence, student, payload.degree());
        studentCompetencesRepository.save(studentCompetence);

        return new HttpEntity<StudentCompetencesGetResponse>(
                HttpStatus.valueOf(201),
                StudentCompetencesGetResponse.buildFromEntity(studentCompetence)
        );
    }

    @Override
    public HttpEntity<StudentCompetencesGetResponse> updateStudentCompetence(Long competenceId, StudentCompetencePayload payload) {
        if(userSession.getProfileRole() == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to edit competences.");
        }

        var competence = studentCompetencesRepository.findById(competenceId)
                .orElseThrow(() -> new ApplicationException(404, "Competence not found."));

        competence.setDegree(payload.degree());
        competence.setUpdatedAt(new Date());
        studentCompetencesRepository.save(competence);

        return new HttpEntity<>(
                HttpStatus.valueOf(200),
                StudentCompetencesGetResponse.buildFromEntity(competence)
        );
    }
}
