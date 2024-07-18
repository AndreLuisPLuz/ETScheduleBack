package ets.schedule.data.payloads;

public record DisciplinePayload(Long groupId, Long instructorId, Long courseId, Integer semester) {
}
