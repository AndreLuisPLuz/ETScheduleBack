package ets.schedule.data.responses.get;

import ets.schedule.models.Disciplines;

import java.util.Date;

public record DisciplineGetResponse(
        Long id,
        String name,
        String group,
        String instructor,
        String course,
        Integer semester,
        Date createdAt,
        Date updatedAt
) {
    public static DisciplineGetResponse buildFromEntity(Disciplines discipline) {
        return new DisciplineGetResponse(
                discipline.getId(),
                discipline.getCourse().getName() + " - " + discipline.getGroup().getName(),
                discipline.getGroup().getName(),
                discipline.getInstructor().getUser().getFullName(),
                discipline.getCourse().getName(),
                discipline.getSemester(),
                discipline.getCreatedAt(),
                discipline.getUpdatedAt()
        );
    }
}
