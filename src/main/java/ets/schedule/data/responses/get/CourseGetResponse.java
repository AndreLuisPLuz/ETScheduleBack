package ets.schedule.data.responses.get;

import ets.schedule.models.Courses;

import java.util.Date;

public record CourseGetResponse(
        Long id,
        String name,
        String description,
        Date createdAt,
        Date updatedAt
) {
    public static CourseGetResponse buildFromEntity(Courses course) {
        return new CourseGetResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}
