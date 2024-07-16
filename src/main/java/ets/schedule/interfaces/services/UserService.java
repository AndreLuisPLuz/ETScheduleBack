package ets.schedule.services;

import java.util.concurrent.CompletableFuture;

import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.models.Users;

public interface UserService {
    CompletableFuture<Users> createUserAsync(UserCreatePayload payload);
}
