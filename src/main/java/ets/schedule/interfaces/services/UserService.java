package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.data.responses.UserResponse;

public interface UserService {
    HttpEntity<UserResponse> createUserAsync(UserCreatePayload payload);
}
