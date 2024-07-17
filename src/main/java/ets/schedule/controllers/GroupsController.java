package ets.schedule.controllers;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.GroupPayload;
import ets.schedule.interfaces.services.GroupService;
import ets.schedule.models.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupsController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/api/v1/group")
    public ResponseEntity<List<Groups>> getAllGroups() {
        try {
            var response = groupService.getAllGroups().get();
            return ResponseEntity.status(response.statusCode()).body(response.data());

        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @GetMapping("/api/v1/group/{id}")
    public ResponseEntity<Groups> getGroupById(@PathVariable("id") Long id) {
        try {
            var response = groupService.getGroupById(id).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());

        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }

    @PostMapping("/api/v1/group")
    public ResponseEntity<Groups> createGroup(@RequestBody GroupPayload obj) {
        try {
            var response = groupService.createGroup(obj).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());

        } catch(Exception ex) {
            throw new ApplicationException(500, "Request could not be completed.");
        }
    }
}
