package ets.schedule.integrations.flask.payloads;

public record LoginPayload(
    String username,
    String password
) { }
