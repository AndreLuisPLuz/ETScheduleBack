package ets.schedule.data.payloads.disciplines;

public record DisciplinePayload(
    Long groupId,
    Long instructorId,
    Long courseId,
    Integer semester,
    String colorCode) {
}
