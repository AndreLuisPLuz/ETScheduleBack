package ets.schedule.data.responses.get;

import ets.schedule.models.StudentCompetences;

import java.util.Date;

public record StudentCompetencesGetResponse(
        Long id,
        Long competenceId,
        Long studentId,
        String degree,
        Date createdAt,
        Date updatedAt
) {
    public static StudentCompetencesGetResponse buildFromEntity(StudentCompetences studentCompetences) {
        return new StudentCompetencesGetResponse(
                studentCompetences.getId(),
                studentCompetences.getCompetence().getId(),
                studentCompetences.getStudent().getId(),
                studentCompetences.getDegree(),
                studentCompetences.getCreatedAt(),
                studentCompetences.getUpdatedAt()
        );
    }
}
