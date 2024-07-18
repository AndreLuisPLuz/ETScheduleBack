package ets.schedule.data.responses.get;

import ets.schedule.models.Events;

import java.util.Date;

public record EventGetResponse(
        Long id,
        Long groupId,
        Long disciplineId,
        Date startsAt,
        Date endsAt,
        String description,
        Date createdAt,
        Date updatedAt
) {
    public static EventGetResponse buildFromEntity(Events event) {
        var groupId = event.getGroup() == null
                ? null
                : event.getGroup().getId();
        
        var disciplineId = event.getDiscipline() == null
                ? null
                : event.getDiscipline().getId();

        return new EventGetResponse(
                event.getId(),
                groupId,
                disciplineId,
                event.getStartsAt(),
                event.getEndsAt(),
                event.getDescription(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
