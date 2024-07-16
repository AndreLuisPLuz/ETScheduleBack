package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

public record HttpEntity<DT> (
    HttpStatusCode statusCode,
    DT data
) { }