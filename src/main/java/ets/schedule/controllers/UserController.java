package ets.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.data.responses.UserResponse;
import ets.schedule.interfaces.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/api/v1/user")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserCreatePayload payload
    ) {
        var response = service.createUserAsync(payload);
        return ResponseEntity.status(response.statusCode()).body(response.data());
    }
}
