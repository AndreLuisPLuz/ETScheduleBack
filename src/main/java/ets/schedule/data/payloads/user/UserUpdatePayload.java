package ets.schedule.data.payloads.user;

import java.util.Optional;

public record UserUpdatePayload(
    String birthDate,
    String fullName,
    String password,
    Optional<Long> groupId
) { }
