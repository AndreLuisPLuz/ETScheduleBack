package ets.schedule.data.responses.get;

import ets.schedule.models.Competences;

import java.util.Date;

public record CompetenceGetResponse(
        Long id,
        Long disciplineId,
        String name,
        Float weight,
        Date createdAt,
        Date updatedAt
) {
    public static CompetenceGetResponse buildFromEntity(Competences competence) {
        return new CompetenceGetResponse(
                competence.getId(),
                competence.getDiscipline().getId(),
                competence.getName(),
                competence.getWeight(),
                competence.getCreatedAt(),
                competence.getUpdatedAt()
        );
    }
}
