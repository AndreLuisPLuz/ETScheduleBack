package ets.schedule.data.responses;

import java.util.Date;

import ets.schedule.models.Users;

public record UserResponse(
    String username,
    String fullName,
    Date birthDate
) {
    public static UserResponse buildFromEntity(Users entity) {
        return new UserResponse(
            entity.getUsername(),
            entity.getFullName(),
            entity.getBirthDate()
        );
    }
}
