package ets.schedule.controllers;

import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.data.responses.get.EventGetResponse;
import ets.schedule.interfaces.services.EventsService;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping("/api/v1/event")
    private ResponseEntity<List<EventGetResponse>> getAllEvents(@RequestParam Integer month, @RequestParam Integer year) {
        var response = eventsService.getAllEvents(month, year);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/event")
    private ResponseEntity<EventGetResponse> createEvent(@RequestBody EventPayload obj) {
        var response = eventsService.createEvent(obj);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
