package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.competence.CompetencePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;

public interface CompetencesService {
    public HttpList<CompetenceGetResponse> getAll();
    public HttpEntity<CompetenceGetResponse> createCompetence(CompetencePayload payload);
    public HttpEntity<CompetenceGetResponse> editCompetence(Long id, CompetencePayload payload);
}