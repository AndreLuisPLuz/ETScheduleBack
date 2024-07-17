package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.groups.GroupPayload;
import ets.schedule.data.responses.get.GroupGetResponse;

public interface GroupsService {
    public HttpList<GroupGetResponse> getAllGroups();
    public HttpEntity<GroupGetResponse> getGroupById(Long id);
    public HttpEntity<GroupGetResponse> createGroup(GroupPayload group);
}
