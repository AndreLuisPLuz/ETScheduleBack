package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

public record HttpGeneric<T> (
    HttpStatusCode statusCode,
    T data
) { }