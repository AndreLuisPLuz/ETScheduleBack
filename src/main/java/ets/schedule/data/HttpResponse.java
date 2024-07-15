package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

import ets.schedule.interfaces.data.DataTransfer;
import ets.schedule.models.BaseModel;

public sealed interface HttpResponse {
    public record EntityResponse<E extends BaseModel, DT extends DataTransfer<E>> (
        HttpStatusCode statusCode,
        DT data
    ) implements HttpResponse { }

    public record GenericResponse<T> (
        HttpStatusCode statusCode,
        T data
    ) implements HttpResponse { }
}