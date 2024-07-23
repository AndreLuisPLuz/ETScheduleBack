package ets.schedule.data.payloads.event;

public record EventUpdatePayload(
    String startsAt,
    String endsAt
) { }
