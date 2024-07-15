package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

public record HttpResponse<T>(
    HttpStatusCode statusCode,
    T data
) { }
