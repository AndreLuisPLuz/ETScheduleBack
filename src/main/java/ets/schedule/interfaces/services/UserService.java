package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.user.UserCreatePayload;
import ets.schedule.data.payloads.user.UserUpdatePayload;
import ets.schedule.data.responses.user.UserResponse;
import ets.schedule.data.responses.user.UserUpdateResponse;

public interface UserService {
    HttpEntity<UserResponse> createUserAsync(UserCreatePayload payload);
    HttpEntity<UserUpdateResponse> updateUser(Long id, UserUpdatePayload payload);
    HttpList<UserResponse> fetchAllUsers(String roleSearch);
}
