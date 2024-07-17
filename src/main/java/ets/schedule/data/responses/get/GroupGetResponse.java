package ets.schedule.data.responses.get;

import ets.schedule.models.Disciplines;
import ets.schedule.models.Events;
import ets.schedule.models.Groups;

import java.util.Date;

public record GroupGetResponse(
        Long id,
        String name,
        Date beginsAt,
        Date endsAt,
        Date createdAt,
        Date updatedAt
) {
    public static GroupGetResponse buildFromEntity(Groups group) {
        return new GroupGetResponse(
                group.getId(),
                group.getName(),
                group.getBeginsAt(),
                group.getEndsAt(),
                group.getCreatedAt(),
                group.getUpdatedAt()
        );
    }
}
