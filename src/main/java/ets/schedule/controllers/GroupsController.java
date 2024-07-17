package ets.schedule.controllers;

import ets.schedule.data.payloads.groups.GroupPayload;
import ets.schedule.data.responses.get.GroupGetResponse;
import ets.schedule.interfaces.services.GroupsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @GetMapping("/api/v1/group")
    public ResponseEntity<List<GroupGetResponse>> getAllGroups() {
        var response = groupsService.getAllGroups();
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @GetMapping("/api/v1/group/{id}")
    public ResponseEntity<GroupGetResponse> getGroupById(@PathVariable("id") Long id) {
        var response = groupsService.getGroupById(id);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PostMapping("/api/v1/group")
    public ResponseEntity<GroupGetResponse> createGroup(@RequestBody GroupPayload obj) {
        var response = groupsService.createGroup(obj);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
