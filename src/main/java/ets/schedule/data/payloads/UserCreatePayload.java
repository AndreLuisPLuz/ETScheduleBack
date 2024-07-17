package ets.schedule.data.payloads;

import java.util.List;

public record UserCreatePayload(
    String username,
    List<String> roles
)
{ }
