package ets.schedule.data.payloads.user;

public record UserUpdatePayload(
    String birthDate,
    String fullName,
    String password
) { }
