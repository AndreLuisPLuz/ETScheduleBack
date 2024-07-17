package ets.schedule.services;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.UserCreatePayload;
import ets.schedule.data.responses.UserResponse;
import ets.schedule.interfaces.services.PasswordService;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.models.Users;
import ets.schedule.models.Profiles;
import ets.schedule.repositories.ProfileJPARepository;
import ets.schedule.repositories.UserJPARepository;

public class DefaultUserService implements UserService {
    @Autowired
    UserJPARepository repo;

    @Autowired
    ProfileJPARepository profileRepo;

    @Autowired
    PasswordService passwordService;

    @Override
    public HttpEntity<UserResponse> createUserAsync(UserCreatePayload payload) {
        var user = new Users();
        user.setUsername(payload.username());
        user.setPassword(passwordService.applyCriptography("Ets@Bosch2020"));

        var createdUser = repo.saveAndFlush(user);

        List<Profiles> profiles;
        try {
            profiles = payload.roles().stream()
                    .map(r -> new Profiles(r, createdUser))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new ApplicationException(
                    400,
                    "Role must be one of admin, instructor or student.");
        }

        var savedProfiles = profileRepo.saveAllAndFlush(profiles);
        createdUser.setProfiles(savedProfiles);

        return new HttpEntity<UserResponse>(
                HttpStatusCode.valueOf(201),
                UserResponse.buildFromEntity(createdUser));
    }

}
