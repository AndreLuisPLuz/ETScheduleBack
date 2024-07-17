package ets.schedule.data.responses.login;

import java.util.List;

import ets.schedule.data.responses.profile.ProfileResponse;

public record AuthConfirmResponse(
    Boolean canLogin,
    List<ProfileResponse> profiles
)
{ }
