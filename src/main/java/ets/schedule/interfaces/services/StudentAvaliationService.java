package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.StudentAvaliationPayload;
import ets.schedule.data.responses.get.StudentAvaliationGetResponse;

public interface StudentAvaliationService {
    public HttpList<StudentAvaliationGetResponse> getAvaliationByDiscipline(Long studentId, Long disciplineId);
    public HttpEntity<StudentAvaliationGetResponse> registerAvaliation(StudentAvaliationPayload studentAvaliation);
    public HttpEntity<StudentAvaliationGetResponse> updateAvaliation(Long studentAvaliationId, StudentAvaliationPayload payload);
}
