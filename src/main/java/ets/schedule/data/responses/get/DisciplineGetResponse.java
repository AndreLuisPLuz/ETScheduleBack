package ets.schedule.data.responses.get;

import ets.schedule.models.Disciplines;

import java.util.Date;

public record DisciplineGetResponse(
        Long id,
        Long groupId,
        Long instructorId,
        Long courseId,
        Integer semester,
        Date createdAt,
        Date updatedAt
) {
    public static DisciplineGetResponse buildFromEntity(Disciplines discipline) {
        return new DisciplineGetResponse(
                discipline.getId(),
                discipline.getGroup().getId(),
                discipline.getInstructor().getId(),
                discipline.getCourse().getId(),
                discipline.getSemester(),
                discipline.getCreatedAt(),
                discipline.getUpdatedAt()
        );
    }
}
