package ets.schedule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.competence.CompetencePayload;
import ets.schedule.data.payloads.courses.CoursePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;
import ets.schedule.data.responses.get.CourseGetResponse;
import ets.schedule.integrations.flask.data.Discipline;
import ets.schedule.interfaces.services.CompetencesService;
import ets.schedule.models.Competences;
import ets.schedule.models.Courses;
import ets.schedule.repositories.CompetencesJPARepository;

public class DefaultCompetencesService implements CompetencesService{
    
    @Autowired 
    private CompetencesJPARepository competencesRepository;

    @Override
    public HttpList<CompetenceGetResponse> getAll() {
        var competences = competencesRepository.findAll()
                .stream()
                .map(CompetenceGetResponse::buildFromEntity)
                .toList();

        return new HttpList<CompetenceGetResponse>(
            HttpStatusCode.valueOf(200),
            competences
        );
    }

    @Override
    public HttpEntity<CompetenceGetResponse> createCompetence(CompetencePayload payload) {
        Competences newCompetence = new Competences();
        
        newCompetence.setDiscipline(payload.discipline());
        newCompetence.setName(payload.name());
        newCompetence.setWeight(payload.weight());

        competencesRepository.save(newCompetence);

        return new HttpEntity<>(
            HttpStatusCode.valueOf(201),
            CompetenceGetResponse.buildFromEntity(newCompetence)
        );
    }

    @Override
    public HttpEntity<CompetenceGetResponse> editCompetence(Long id, CompetencePayload payload) {

        var competence = competencesRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(404, "Competence not found."));

        if (payload.name() != null && !payload.name().isEmpty()) {
            competence.setName(payload.name());
        }

        if (payload.weight() != null) {
            competence.setWeight(payload.weight());
        }

        competencesRepository.save(competence);

        return new HttpEntity<>(
            HttpStatusCode.valueOf(201),
            CompetenceGetResponse.buildFromEntity(competence)
        );
    }


}