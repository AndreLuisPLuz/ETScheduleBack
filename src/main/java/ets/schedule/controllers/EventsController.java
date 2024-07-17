package ets.schedule.controllers;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.data.responses.get.EventGetResponse;
import ets.schedule.interfaces.services.EventsService;
import ets.schedule.models.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping("/api/v1/event")
    private ResponseEntity<List<EventGetResponse>> getAllEvents() {
        var response = eventsService.getAllEvents();
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/event")
    private ResponseEntity<EventGetResponse> createEvent(EventPayload obj) {
        var response = eventsService.createEvent(obj);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
