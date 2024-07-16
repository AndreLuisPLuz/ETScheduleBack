package ets.schedule.data;

import org.springframework.http.HttpStatusCode;

import ets.schedule.interfaces.data.DataTransfer;
import ets.schedule.models.BaseModel;

public record EntityResponse<E extends BaseModel> (
    HttpStatusCode statusCode,
    DataTransfer<E> data
) { }