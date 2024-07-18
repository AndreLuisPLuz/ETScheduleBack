package ets.schedule.data.responses.get;

import ets.schedule.models.StudentAvaliation;

import java.util.Date;

public record StudentAvaliationGetResponse(
        Long id,
        Long disciplineId,
        Long studentId,
        String comment,
        Date createdAt,
        Date updatedAt
) {
    public static StudentAvaliationGetResponse buildFromEntity(StudentAvaliation studentAvaliation) {
        return new StudentAvaliationGetResponse(
                studentAvaliation.getId(),
                studentAvaliation.getDiscipline().getId(),
                studentAvaliation.getStudent().getId(),
                studentAvaliation.getComment(),
                studentAvaliation.getCreatedAt(),
                studentAvaliation.getUpdatedAt()
        );
    }
}
