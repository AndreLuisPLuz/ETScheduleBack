package ets.schedule.interfaces.services;

import java.util.concurrent.CompletableFuture;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.data.responses.UserResponse;

public interface UserService {
    CompletableFuture<HttpEntity<UserResponse>> createUserAsync(UserCreatePayload payload);
}
