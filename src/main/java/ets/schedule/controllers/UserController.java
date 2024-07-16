package ets.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntityResponse;
import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.models.Users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/api/v1/user")
    public ResponseEntity<HttpEntityResponse<Users>> createUser(
            @RequestBody UserCreatePayload payload
    ) {
        try {
            var createdUser = service.createUserAsync(payload).get();
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        } catch(Exception ex) {
            throw new ApplicationException(
                HttpStatusCode.valueOf(500),
                "Request could not be completed."
            );
        }
    }
}
