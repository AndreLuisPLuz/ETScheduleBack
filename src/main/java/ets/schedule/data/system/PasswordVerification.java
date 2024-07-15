package ets.schedule.data.system;

public record PasswordVerification(
    boolean isValid,
    int parametersMet
) { }