package ets.schedule.data.payloads.competence;

import ets.schedule.models.Disciplines;

public record CompetencePayload(Disciplines discipline, String name, Float weight) {}

