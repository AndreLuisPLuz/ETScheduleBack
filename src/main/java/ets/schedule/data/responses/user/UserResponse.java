package ets.schedule.data.responses.user;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ets.schedule.models.Users;

public record UserResponse(
    Long id,
    String username,
    String fullName,
    Date birthDate,
    List<String> roles
) {
    public static UserResponse buildFromEntity(Users entity) {
        var roles = entity.getProfiles()
                .stream()
                .map(p -> p.getRole().getRole())
                .collect(Collectors.toList());

        return new UserResponse(
            entity.getId(),
            entity.getUsername(),
            entity.getFullName(),
            entity.getBirthDate(),
            roles
        );
    }
}
