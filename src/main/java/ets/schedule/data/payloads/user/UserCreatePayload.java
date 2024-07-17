package ets.schedule.data.payloads.user;

import java.util.List;

public record UserCreatePayload(
    String username,
    List<String> roles
)
{ }
