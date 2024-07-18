package ets.schedule.data.payloads.user;

import java.util.List;
import java.util.Optional;

public record UserCreatePayload(
    String username,
    List<String> roles,
    Optional<Long> groupId
)
{ }
