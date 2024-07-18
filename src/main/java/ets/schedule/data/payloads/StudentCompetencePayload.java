package ets.schedule.data.payloads;

public record StudentCompetencePayload(
        Long competenceId,
        Long studentId,
        String degree
) {
}
