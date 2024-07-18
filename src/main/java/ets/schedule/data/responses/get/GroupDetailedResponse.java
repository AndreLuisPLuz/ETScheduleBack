package ets.schedule.data.responses.get;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.responses.profile.ProfileResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.models.Groups;

public record GroupDetailedResponse(
    Long id,
    String name,
    Date beginsAt,
    Date endsAt,
    List<ProfileResponse> students
) {
    public static GroupDetailedResponse buildFromEntity(Groups group) {
        var students = group.getProfiles()
                .stream()
                .filter(p -> p.getRole() == ProfileRole.Student)
                .collect(Collectors.toList());
        
        if (students == null) {
            throw new ApplicationException(500, "User data couldn't be fetched on server.");
        }

        return new GroupDetailedResponse(
                group.getId(),
                group.getName(),
                group.getBeginsAt(),
                group.getEndsAt(),
                students.stream()
                    .map(s -> ProfileResponse.buildFromEntity(s))
                    .collect(Collectors.toList())
        );
    }
}
