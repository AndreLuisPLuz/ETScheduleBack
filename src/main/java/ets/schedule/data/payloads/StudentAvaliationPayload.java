package ets.schedule.data.payloads;

public record StudentAvaliationPayload(
        Long disciplineId,
        Long studentId,
        String comment
) {
}
