package ets.schedule.services;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.event.EventPayload;
import ets.schedule.data.responses.get.EventGetResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.EventsService;
import ets.schedule.models.Events;
import ets.schedule.repositories.DisciplinesJPARepository;
import ets.schedule.repositories.EventsJPARepository;
import ets.schedule.repositories.GroupsJPARepository;
import ets.schedule.repositories.ProfilesJPARepository;
import ets.schedule.sessions.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DefaultEventsService implements EventsService {

    @Autowired
    private EventsJPARepository eventsJPARepository;

    @Autowired
    private GroupsJPARepository groupsJPARepository;

    @Autowired
    private DisciplinesJPARepository disciplinesJPARepository;

    @Autowired
    private ProfilesJPARepository profilesJPARepository;

    @Autowired
    private UserSession userSession;

    @Override
    public HttpList<EventGetResponse> getAllEvents(Integer month, Integer year) {
        var profile = profilesJPARepository.findById(userSession.getProfileId())
                .orElseThrow(() -> new ApplicationException(403, "User profile not found"));

        List<EventGetResponse> events = null;

        if(profile.getRole() == ProfileRole.Admin) {
            events = eventsJPARepository.findAll()
                    .stream()
                    .map(EventGetResponse::buildFromEntity)
                    .toList();
        }

        if(profile.getRole() == ProfileRole.Student) {
            var group = profile.getGroup();

            events = eventsJPARepository.findByMonthAndYear(month, year, group.getId())
                    .stream()
                    .map(EventGetResponse::buildFromEntity)
                    .toList();
        }

        if(profile.getRole() == ProfileRole.Instructor) {
            var test = eventsJPARepository.findByInstructorAndDate("0005-05-10 12:00:00.000000", 4L);
//            ..query not working, hibernate says column id does not exist :(..
        }

        return new HttpList<>(
                HttpStatus.valueOf(200),
                events
        );
    }

    @Override
    public HttpEntity<EventGetResponse> createEvent(EventPayload obj) {
        var profile = profilesJPARepository.findById(userSession.getProfileId())
                .orElseThrow(() -> new ApplicationException(403, "User profile not found."));

        if(userSession.getProfileRole() == ProfileRole.Student) {
            throw new ApplicationException(403, "User does not have permission to create events.");
        }

        if(userSession.getProfileRole() == ProfileRole.Instructor) {
            var discipline = disciplinesJPARepository.findById(obj.disciplineId())
                    .orElseThrow(() -> new ApplicationException(404, "Discipline not found."));

            if(!profile.getDisciplines().contains(discipline)) {
                throw new ApplicationException(403, "User does not have permission to create event outside their disciplines.");
            }
        }

        var newEvent = new Events(
                formatDateFromString(obj.startsAt()),
                formatDateFromString(obj.endsAt()),
                obj.description());

        var group = groupsJPARepository.findById(obj.groupId()).
                orElseThrow(() -> new ApplicationException(404, "Group could not be found."));

        var discipline = disciplinesJPARepository.findById(obj.disciplineId())
                .orElseThrow(() -> new ApplicationException(404, "Discipline could not be found."));

        newEvent.setGroup(group);
        newEvent.setDiscipline(discipline);
        group.getEvents().add(newEvent);
        discipline.getEvents().add(newEvent);

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
