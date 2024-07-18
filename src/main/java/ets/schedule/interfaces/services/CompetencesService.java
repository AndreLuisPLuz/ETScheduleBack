package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CompetencePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;
import ets.schedule.models.Competences;

import java.util.List;

public interface CompetencesService {
    public HttpList<CompetenceGetResponse> getAllCompetencesByDiscipline(Long id);
    public HttpEntity<CompetenceGetResponse> createCompetence(Long disciplineId, CompetencePayload competence);
}
