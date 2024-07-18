package ets.schedule.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.user.UserCreatePayload;
import ets.schedule.data.payloads.user.UserUpdatePayload;
import ets.schedule.data.responses.user.UserResponse;
import ets.schedule.data.responses.user.UserUpdateResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.sessions.UserSession;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    UserSession session;

    @PostMapping("/api/v1/user")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserCreatePayload payload
    ) {
        if (session.getProfileRole() != ProfileRole.Admin)
            throw new ApplicationException(403, "User can't perform this action.");
        
        var response = service.createUserAsync(payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @PatchMapping("/api/v1/user")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @RequestBody UserUpdatePayload payload
    ) {
        var requestingUserId = session.getUserId();
        var response = service.updateUser(requestingUserId, payload);

        return ResponseEntity.status(response.statusCode()).body(response.data());
    }

    @GetMapping("/api/v1/user")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String role
    ) {
        var response = service.fetchAllUsers(role);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
    
}
