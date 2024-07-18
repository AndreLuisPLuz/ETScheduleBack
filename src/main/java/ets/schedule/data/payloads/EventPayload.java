package ets.schedule.data.payloads;

public record EventPayload(Long groupId, Long disciplineId, String startsAt, String endsAt, String description) {
}
