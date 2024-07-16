package ets.schedule.data.responses.profile;

import ets.schedule.models.Profiles;

public record ProfileResponse(
    String role
) {
    public static ProfileResponse buildFromEntity(Profiles profile) {
        return new ProfileResponse(profile.getRole().getRole());
    }
}