package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.groups.GroupPayload;
import ets.schedule.data.responses.get.GroupGetResponse;
import ets.schedule.interfaces.services.GroupsService;
import ets.schedule.models.Groups;
import ets.schedule.repositories.GroupsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class DefaultGroupService implements GroupsService {

    @Autowired
    private GroupsJPARepository groupsJPARepository;

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
    public HttpEntity<GroupGetResponse> getGroupById(Long id) {
        var group = groupsJPARepository.findById(id);
        if(group.isEmpty()) {
            throw new ApplicationException(404, "Group could not be found.");
        }

        return new HttpEntity<GroupGetResponse>(
                HttpStatusCode.valueOf(200),
                GroupGetResponse.buildFromEntity(group.get())
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
