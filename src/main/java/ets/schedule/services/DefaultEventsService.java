package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.data.responses.get.EventGetResponse;
import ets.schedule.interfaces.services.EventsService;
import ets.schedule.models.Events;
import ets.schedule.repositories.DisciplinesJPARepository;
import ets.schedule.repositories.EventsJPARepository;
import ets.schedule.repositories.GroupsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class DefaultEventsService implements EventsService {

    @Autowired
    private EventsJPARepository eventsJPARepository;

    @Autowired
    private GroupsJPARepository groupsJPARepository;

    @Autowired
    private DisciplinesJPARepository disciplinesJPARepository;

    @Override
    public HttpList<EventGetResponse> getAllEvents() {
        var events = eventsJPARepository.findAll().stream().map(
                EventGetResponse::buildFromEntity
        );

        return new HttpList<>(
                HttpStatus.valueOf(200),
                events.toList()
        );
    }

    @Override
    public HttpEntity<EventGetResponse> createEvent(EventPayload obj) {
        var newEvent = new Events(
                formatDateFromString(obj.startsAt()),
                formatDateFromString(obj.endsAt()),
                obj.description());

        var group = groupsJPARepository.findById(obj.groupId());
        var discipline = disciplinesJPARepository.findById(obj.disciplineId());
        if(group.isEmpty()) {
            throw new ApplicationException(404, "Group could not be found.");
        } else if(discipline.isEmpty()) {
            throw new ApplicationException(404, "Discipline could not be found.");
        }

        newEvent.setGroup(group.get());
        newEvent.setDiscipline(discipline.get());

        eventsJPARepository.save(newEvent);

        return new HttpEntity<>(
                HttpStatus.valueOf(201),
                EventGetResponse.buildFromEntity(newEvent)
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
