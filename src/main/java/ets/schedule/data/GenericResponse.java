package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

public record GenericResponse<T> (
    HttpStatusCode statusCode,
    T data
) { }