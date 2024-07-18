package ets.schedule.services;

import java.util.stream.Collectors;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.user.UserCreatePayload;
import ets.schedule.data.payloads.user.UserUpdatePayload;
import ets.schedule.data.responses.user.UserResponse;
import ets.schedule.data.responses.user.UserUpdateResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.PasswordService;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.models.Users;
import ets.schedule.models.Profiles;
import ets.schedule.repositories.GroupsJPARepository;
import ets.schedule.repositories.ProfileJPARepository;
import ets.schedule.repositories.UserJPARepository;

public class DefaultUserService implements UserService {
    @Autowired
    UserJPARepository repo;

    @Autowired
    ProfileJPARepository profileRepo;

    @Autowired
    GroupsJPARepository groupRepo;

    @Autowired
    PasswordService passwordService;

    @Override
    public HttpEntity<UserResponse> createUserAsync(UserCreatePayload payload) {
        var user = new Users();
        user.setUsername(payload.username());
        user.setPassword(passwordService.applyCriptography("Ets@Bosch2024"));

        var createdUser = repo.saveAndFlush(user);
        
        List<Profiles> profiles;
        try {
            profiles = payload.roles()
                    .stream()
                    .map(r -> new Profiles(r, createdUser))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new ApplicationException(400, "Role must be one of admin, instructor or student.");
        }

        boolean isStudent = profiles.stream().
                anyMatch(p -> p.getRole() == ProfileRole.Student);

        if (isStudent)
            addGroupToProfiles(payload.groupId(), profiles);

        var savedProfiles = profileRepo.saveAllAndFlush(profiles);
        createdUser.setProfiles(savedProfiles);

        return new HttpEntity<UserResponse>(
                HttpStatusCode.valueOf(201),
                UserResponse.buildFromEntity(createdUser));
    }

    @Override
    public HttpEntity<UserUpdateResponse> updateUser(
            Long id,
            UserUpdatePayload payload) {
        boolean fullNameMissing = payload.fullName().isEmpty();
        boolean birthDateMissing = payload.birthDate().isEmpty();

        if (fullNameMissing)
            throw new ApplicationException(400, "User's full name must be given.");

        if (birthDateMissing)
            throw new ApplicationException(400, "User's birth date must be given.");

        var fetchUser = repo.findById(id);
        if (!fetchUser.isPresent())
            throw new ApplicationException(404, "User not found.");

        var user = fetchUser.get();

        var passwordVerification = passwordService.verifyPrerequisites(
                payload.password());

        if (!passwordVerification.isValid())
            return new HttpEntity<UserUpdateResponse>(
                    HttpStatusCode.valueOf(400),
                    new UserUpdateResponse.Error(passwordVerification));

        user.setFullName(payload.fullName());
        user.setBirthDate(Date.valueOf(payload.birthDate()));
        user.setPassword(passwordService.applyCriptography(
                payload.password() != null
                        ? payload.password()
                        : "Ets@Bosch2024"));

        var savedUser = repo.save(user);

        System.out.println("change");

        return new HttpEntity<UserUpdateResponse>(
                HttpStatusCode.valueOf(200),
                UserUpdateResponse.Ok.buildFromEntity(savedUser));
    }

    private void addGroupToProfiles(Optional<Long> groupId, List<Profiles> profiles) {
        if (!groupId.isPresent())
            throw new ApplicationException(
                400, "groupId must be informed if the user is a student.");

        var group = groupRepo.findById(groupId.get());

        if (!group.isPresent())
            throw new ApplicationException(404, "Group not found.");

        var profile = profiles.stream()
                .filter(p -> p.getRole() == ProfileRole.Student)
                .collect(Collectors.toList())
                .get(0);

        profile.setGroup(group.get());
    }
}
