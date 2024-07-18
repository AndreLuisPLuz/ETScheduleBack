package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CompetencePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;
import ets.schedule.data.responses.get.CourseGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.CompetencesService;
import ets.schedule.models.Competences;
import ets.schedule.repositories.CompetencesJPARepository;
import ets.schedule.repositories.DisciplinesJPARepository;
import ets.schedule.sessions.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public class DefaultCompetencesService implements CompetencesService {

    @Autowired
    private CompetencesJPARepository competencesRepository;

    @Autowired
    private DisciplinesJPARepository disciplinesRepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<CompetenceGetResponse> getAllCompetencesByDiscipline(Long id) {
        var discipline = disciplinesRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(404, "Discipline not found"));

        return new HttpList<CompetenceGetResponse>(
                HttpStatusCode.valueOf(200),
                discipline.getCompetences()
                        .stream()
                        .map(CompetenceGetResponse::buildFromEntity)
                        .toList()
        );
    }

    @Override
    public HttpEntity<CompetenceGetResponse> createCompetence(Long disciplineId, CompetencePayload payload) {
        if(userSession.getProfileRole() == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to create a competence");
        }

        var discipline = disciplinesRepository.findById(disciplineId)
                .orElseThrow(() -> new ApplicationException(404, "Discipline not found"));

        var competence = Competences.build(discipline, payload.name(), payload.weight());
        competencesRepository.save(competence);

        return new HttpEntity<CompetenceGetResponse>(
                HttpStatusCode.valueOf(200),
                CompetenceGetResponse.buildFromEntity(competence)
        );
    }
}
