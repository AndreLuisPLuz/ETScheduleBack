package ets.schedule.data.payloads;

public record InstructorSkillPayload(
        Long instructorId,
        String subject,
        Integer value
) {
}
