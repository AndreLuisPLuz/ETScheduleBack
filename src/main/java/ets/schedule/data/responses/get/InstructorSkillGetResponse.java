package ets.schedule.data.responses.get;

import ets.schedule.models.InstructorSkills;

import java.util.Date;

public record InstructorSkillGetResponse(
        Long id,
        Long instructorId,
        String subject,
        Integer value,
        Date createdAt,
        Date updatedAt
) {
    public static InstructorSkillGetResponse buildFromEntiy(InstructorSkills instructorSkills) {
        return new InstructorSkillGetResponse(
                instructorSkills.getId(),
                instructorSkills.getInstructor().getId(),
                instructorSkills.getSubject(),
                instructorSkills.getValue(),
                instructorSkills.getCreatedAt(),
                instructorSkills.getUpdatedAt()
        );
    }
}
