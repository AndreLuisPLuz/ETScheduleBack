package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CompetencePayload;
import ets.schedule.data.payloads.StudentCompetencePayload;
import ets.schedule.data.responses.get.CompetenceGetResponse;
import ets.schedule.data.responses.get.StudentCompetencesGetResponse;

public interface StudentCompetencesService {
    public HttpList<StudentCompetencesGetResponse> getStudentCompetences(Long disciplineId, Long studentId);
    public HttpEntity<StudentCompetencesGetResponse> registerStudentCompetence(StudentCompetencePayload payload);
    public HttpEntity<StudentCompetencesGetResponse> updateStudentCompetence(Long competenceId, StudentCompetencePayload payload);
}
