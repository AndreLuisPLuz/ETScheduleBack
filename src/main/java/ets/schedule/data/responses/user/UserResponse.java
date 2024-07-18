package ets.schedule.data.responses.user;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ets.schedule.models.Profiles;
import ets.schedule.models.Users;

public record UserResponse(
    Long id,
    String username,
    String fullName,
    Date birthDate,
    List<Role> roles
) {
    protected final record Role(
        String role,
        Long id
    ) {
        protected static Role buildFromEntity(Profiles profile) {
            return new Role(
                profile.getRole().getRole(), 
                profile.getId());
        }
    }

    public static UserResponse buildFromEntity(Users entity) {
        var roles = entity.getProfiles()
                .stream()
                .map(Role::buildFromEntity)
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
