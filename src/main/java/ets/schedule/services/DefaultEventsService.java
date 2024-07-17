package ets.schedule.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.interfaces.services.EventsService;
import ets.schedule.models.Events;
import ets.schedule.repositories.DisciplinesRepository;
import ets.schedule.repositories.EventsRepository;
import ets.schedule.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class DefaultEventsService implements EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private DisciplinesRepository disciplinesRepository;

    @Override
    public CompletableFuture<HttpList<Events>> getAllEvents() {
        return CompletableFuture.supplyAsync(() -> {
            return new HttpList<>(
                    HttpStatus.valueOf(200),
                    eventsRepository.findAll()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Events>> createEvent(EventPayload obj) {
        return CompletableFuture.supplyAsync(() -> {

            var newEvent = new Events(
                    formatDateFromString(obj.startsAt()),
                    formatDateFromString(obj.endsAt()),
                    obj.description());

            var group = groupsRepository.findById(obj.groupId());
            var discipline = disciplinesRepository.findById(obj.disciplineId());
//            if(group.isEmpty() || discipline.isEmpty()) {
//                throw new NotFoundException("A resource was not found.");
//            }

            newEvent.setGroup(group.get());
            newEvent.setDiscipline(discipline.get());

            return new HttpEntity<>(
                    HttpStatus.valueOf(201),
                    eventsRepository.save(newEvent)
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
