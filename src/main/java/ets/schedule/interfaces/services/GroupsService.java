package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.GroupPayload;
import ets.schedule.models.Groups;

import java.util.concurrent.CompletableFuture;

public interface GroupsService {
    public CompletableFuture<HttpList<Groups>> getAllGroups();
    public CompletableFuture<HttpEntity<Groups>> getGroupById(Long id);
    public CompletableFuture<HttpEntity<Groups>> createGroup(GroupPayload group);
}
