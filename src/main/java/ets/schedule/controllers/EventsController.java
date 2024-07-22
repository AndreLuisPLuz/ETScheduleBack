package ets.schedule.controllers;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.data.payloads.event.EventUpdatePayload;
import ets.schedule.data.responses.get.EventGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.EventsService;
import ets.schedule.sessions.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {
    @Autowired
    private EventsService eventsService;

    @Autowired
    private UserSession userSession;

    @GetMapping("/api/v1/event")
    private ResponseEntity<List<EventGetResponse>> getAllEvents(@RequestParam Integer month, @RequestParam Integer year) {
        var response = eventsService.getAllEvents(month, year);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/event")
    private ResponseEntity<EventGetResponse> createEvent(@RequestBody EventPayload obj) {
        var role = userSession.getProfileRole();

        if(role == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to create events.");
        }

        var response = eventsService.createEvent(obj);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PatchMapping("/api/v1/event/{id}")
    private ResponseEntity<Void> updateEvent(
            @PathVariable Long id,
            @RequestBody EventUpdatePayload payload
    ) {
        var response = eventsService.updateEvent(id, payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
