package ets.schedule.data.responses.login;

public record AuthResponse(
    String message,
    String token,
    Long userId
)
{ }
