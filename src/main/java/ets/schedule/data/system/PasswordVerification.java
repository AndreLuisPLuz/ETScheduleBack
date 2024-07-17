package ets.schedule.data.system;

public record PasswordVerification(
    boolean isValid,
    boolean hasRequiredLength,
    boolean hasUpperCase,
    boolean hasLowerCase,
    boolean hasNumber,
    boolean hasSpecialChar
) { }