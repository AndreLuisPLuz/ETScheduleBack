package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.groups.GroupPayload;
import ets.schedule.data.responses.get.GroupDetailedResponse;
import ets.schedule.data.responses.get.GroupGetResponse;
import ets.schedule.interfaces.services.GroupsService;
import ets.schedule.models.Groups;
import ets.schedule.repositories.GroupsJPARepository;
import ets.schedule.repositories.UserJPARepository;
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
    private UserJPARepository userJPARepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<GroupGetResponse> getAllGroups() {
        var groups = groupsJPARepository.findAll().stream().map(
                GroupGetResponse::buildFromEntity
        );

        return new HttpList<GroupGetResponse>(
                HttpStatusCode.valueOf(200),
                groups.toList()
        );
    }

    @Override
    public HttpEntity<GroupDetailedResponse> getGroupById(Long id) {
        var groupFetch = groupsJPARepository.findById(id);
        if(groupFetch.isEmpty()) {
            throw new ApplicationException(404, "Group could not be found.");
        }

        var group = groupFetch.get();

        var user = userJPARepository.findById(userSession.getUserId()).get();
        for (var p : groupFetch.get().getProfiles())
            p.setUser(user);

        return new HttpEntity<GroupDetailedResponse>(
                HttpStatusCode.valueOf(200),
                GroupDetailedResponse.buildFromEntity(group)
        );
    }

    @Override
    public HttpEntity<GroupGetResponse> createGroup(GroupPayload group) {
        var newGroup = new Groups(group.name(),
                formatDateFromString(group.beginsAt()),
                formatDateFromString(group.endsAt()));

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
