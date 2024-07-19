package ets.schedule.data.payloads.event;

import java.util.Optional;

public record EventPayload(
    Optional<Long> groupId,
    Optional<Long> disciplineId,
    String startsAt,
    String endsAt,
    String description) { }
