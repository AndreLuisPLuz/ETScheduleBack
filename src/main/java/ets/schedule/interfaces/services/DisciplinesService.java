package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.disciplines.DisciplinePayload;
import ets.schedule.models.Disciplines;

import java.util.concurrent.CompletableFuture;

public interface DisciplinesService {
    public CompletableFuture<HttpList<Disciplines>> getAllDisciplines();
    public CompletableFuture<HttpEntity<Disciplines>> getDisciplineById(Long id);
    public CompletableFuture<HttpEntity<Disciplines>> createDiscipline(DisciplinePayload obj);
}
