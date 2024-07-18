package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.GroupPayload;
import ets.schedule.data.responses.get.GroupDetailedResponse;
import ets.schedule.data.responses.get.GroupGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.GroupsService;
import ets.schedule.models.Groups;
import ets.schedule.repositories.GroupsJPARepository;
import ets.schedule.sessions.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultGroupService implements GroupsService {

    @Autowired
    private GroupsJPARepository groupsJPARepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<GroupGetResponse> getAllGroups() {
        if(userSession.getProfileRole() == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to view groups.");
        }

        var groups = groupsJPARepository.findAll()
                .stream()
                .map(GroupGetResponse::buildFromEntity)
                .toList();

        return new HttpList<GroupGetResponse>(
                HttpStatusCode.valueOf(200),
                groups
        );
    }

    @Override
    public HttpEntity<GroupDetailedResponse> getGroupById(Long id) {
        var group = groupsJPARepository.findById(id)
                .orElseThrow(() -> new ApplicationException(404, "Group not found"));

        return new HttpEntity<GroupDetailedResponse>(
                HttpStatusCode.valueOf(200),
                GroupDetailedResponse.buildFromEntity(group)
        );
    }

    @Override
    public HttpEntity<GroupGetResponse> createGroup(GroupPayload payload) {
        if(userSession.getProfileRole() != ProfileRole.Admin) {
            throw new ApplicationException(403, "User does not have permission to add groups.");
        }

        var newGroup = Groups.build(
                payload.name(),
                formatDateFromString(payload.beginsAt()),
                formatDateFromString(payload.endsAt())
        );

        groupsJPARepository.save(newGroup);

        return new HttpEntity<GroupGetResponse>(
                HttpStatusCode.valueOf(200),
                GroupGetResponse.buildFromEntity(newGroup)
        );
    }

    public Date formatDateFromString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'");

        dateFormat.setLenient(false);

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
