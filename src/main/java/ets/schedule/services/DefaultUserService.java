package ets.schedule.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.data.responses.UserResponse;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.models.Users;
import ets.schedule.repositories.UserJPARepository;

public class DefaultUserService implements UserService {
    @Autowired
    UserJPARepository repository;

    @Override
    public CompletableFuture<HttpEntity<UserResponse>> createUserAsync(UserCreatePayload payload) {
        return CompletableFuture.supplyAsync(() -> {
            var user = new Users();
            user.setUsername(payload.username());
            user.setPassword("Ets@Bosch2024");

            var createdUser = repository.save(user);

            return new HttpEntity<UserResponse>(
                HttpStatusCode.valueOf(401),
                UserResponse.buildFromEntity(createdUser)
            );
        });
    }

}
