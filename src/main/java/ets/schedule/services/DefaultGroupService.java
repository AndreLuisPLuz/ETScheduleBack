package ets.schedule.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.GroupPayload;
import ets.schedule.interfaces.services.GroupsService;
import ets.schedule.models.Groups;
import ets.schedule.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class DefaultGroupService implements GroupsService {

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

            var newGroup = new Groups(group.name(),
                    formatDateFromString(group.beginsAt()),
                    formatDateFromString(group.endsAt()));

            return new HttpEntity<Groups>(
                    HttpStatusCode.valueOf(200),
                    groupsRepository.save(newGroup)
            );
        });
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
