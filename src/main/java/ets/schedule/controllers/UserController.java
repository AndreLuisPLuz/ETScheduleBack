package ets.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ets.schedule.Exceptions.ApplicationException;
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
        try {
            var response = service.createUserAsync(payload).get();
            return ResponseEntity.status(response.statusCode()).body(response.data());
        } catch(Exception ex) {
            throw new ApplicationException(
                HttpStatusCode.valueOf(500),
                "Request could not be completed."
            );
        }
    }
}
