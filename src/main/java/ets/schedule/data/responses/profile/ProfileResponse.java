package ets.schedule.data.responses.profile;

import ets.schedule.models.Profiles;

public record ProfileResponse(
    String name,
    String role
) {
    public static ProfileResponse buildFromEntity(Profiles profile) {
        return new ProfileResponse(
            (profile.getUser().getFullName() != null)
                ? profile.getUser().getFullName()
                : profile.getUser().getUsername(),
            profile.getRole().getRole()
        );
    }
}