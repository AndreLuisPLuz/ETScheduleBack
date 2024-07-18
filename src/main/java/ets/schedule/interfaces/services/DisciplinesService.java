package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.DisciplinePayload;
import ets.schedule.data.responses.get.DisciplineGetResponse;

public interface DisciplinesService {
    public HttpList<DisciplineGetResponse> getAllDisciplines();
    public HttpEntity<DisciplineGetResponse> getDisciplineById(Long id);
    public HttpEntity<DisciplineGetResponse> createDiscipline(DisciplinePayload obj);
}
