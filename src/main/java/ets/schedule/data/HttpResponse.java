package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

import ets.schedule.interfaces.data.DataTransfer;
import ets.schedule.models.BaseModel;

public record HttpResponse<E extends BaseModel, DT extends DataTransfer<E>>(
    HttpStatusCode statusCode,
    DT data
) { }
