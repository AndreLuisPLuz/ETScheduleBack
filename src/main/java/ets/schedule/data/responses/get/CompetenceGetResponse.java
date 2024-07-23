package ets.schedule.data.responses.get;

import ets.schedule.models.Competences;

public record CompetenceGetResponse(
        Long discipline_id,
        String name,
        Float weight
) {
    public static CompetenceGetResponse buildFromEntity(Competences competences) {
        return new CompetenceGetResponse(
            competences.getId(),
            competences.getName(),
            competences.getWeight()
        );
    }
}

