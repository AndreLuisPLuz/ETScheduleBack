package ets.schedule.data.payloads.login;

public record LoginPayload(
    String username,
    String password,
    String role
)
{ }
