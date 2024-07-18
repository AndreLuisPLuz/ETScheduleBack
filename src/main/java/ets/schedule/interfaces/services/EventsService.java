package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.data.responses.get.EventGetResponse;

public interface EventsService {
    public HttpList<EventGetResponse> getAllEvents(Integer month, Integer year);
    public HttpEntity<EventGetResponse> createEvent(EventPayload obj);
}
