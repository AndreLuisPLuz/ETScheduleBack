package ets.schedule.interfaces.data;

import ets.schedule.models.BaseModel;

public interface DataTransfer<E extends BaseModel> {
    DataTransfer<E> buildFromEntity(E entity);
}
