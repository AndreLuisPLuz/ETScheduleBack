package ets.schedule.services;

import ets.schedule.Exceptions.NotFoundException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.GroupPayload;
import ets.schedule.interfaces.services.GroupService;
import ets.schedule.models.Groups;
import ets.schedule.repositories.GroupsRepository;
import org.apache.catalina.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DefaultGroupService implements GroupService {

    @Autowired
    private GroupsRepository groupsRepository;

    @Override
    public CompletableFuture<HttpList<Groups>> getAllGroups() {
        return CompletableFuture.supplyAsync(() -> {
            return new HttpList<Groups>(
                    HttpStatusCode.valueOf(200),
                    groupsRepository.findAll()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Groups>> getGroupById(Long id) {
        return CompletableFuture.supplyAsync(() -> {

            var group = groupsRepository.findById(id);
//        if(group.isEmpty()) {
//            throw new NotFoundException("Group was not found.");
//        }

            return new HttpEntity<Groups>(
                    HttpStatusCode.valueOf(200),
                    group.get()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Groups>> createGroup(GroupPayload group) {
        return CompletableFuture.supplyAsync(() -> {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'");
            dateFormat.setLenient(false);
            Date beginsAt = null;
            Date endsAt = null;

            try {
                beginsAt = dateFormat.parse(group.beginsAt());
                endsAt = dateFormat.parse(group.endsAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            var newGroup = new Groups(group.name(), beginsAt, endsAt);

            return new HttpEntity<Groups>(
                    HttpStatusCode.valueOf(200),
                    groupsRepository.save(newGroup)
            );
        });
    }

}
