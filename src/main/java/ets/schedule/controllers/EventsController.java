package ets.schedule.controllers;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.EventPayload;
import ets.schedule.interfaces.services.EventsService;
import ets.schedule.models.Events;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping("/api/v1/event")
    private ResponseEntity<List<Events>> getAllEvents() {
        try {
            var response = eventsService.getAllEvents().get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch (Exception e) {
            throw new ApplicationException(500, "Request could not be completed");
        }
    }

    @PostMapping("/api/v1/event")
    private ResponseEntity<Events> createEvent(EventPayload obj) {
        try {
            var response = eventsService.createEvent(obj).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch (Exception e) {
            throw new ApplicationException(500, "Request could not be completed");
        }
    }
}
