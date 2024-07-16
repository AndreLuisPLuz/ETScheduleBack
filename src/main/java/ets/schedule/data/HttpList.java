package ets.schedule.data;

import org.springframework.http.HttpStatusCode;
import java.util.List;

public record HttpList<T> (
    HttpStatusCode statusCode,
    List<T> data
) { }