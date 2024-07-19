package ets.schedule.data.responses.instructor;

import ets.schedule.models.Profiles;

public record InstructorResponse(
    String name,
    Long profileId
) {
    public static InstructorResponse buildFromEntity(Profiles instructor) {
        return new InstructorResponse(
            instructor.getUser().getUsername(),
            instructor.getId()
        );
    }
}
