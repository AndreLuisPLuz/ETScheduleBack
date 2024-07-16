package ets.schedule.sessions;

import ets.schedule.enums.ProfileRole;

public record UserSession(
    Long userId,
    ProfileRole profileRole
) { }
