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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
            var initialDate = new GregorianCalendar(year, month, 1);
            var finalDate = new GregorianCalendar(year, month, 
                    GregorianCalendar.getInstance()
                        .getActualMaximum(GregorianCalendar.DAY_OF_MONTH
            ));

            var allEvents = eventsJPARepository.findByInstructorId(profile.getId());

            events = allEvents.stream()
                    .filter(e -> {
                        var startsAt = castToCalendar(e.getStartsAt());
                        var endsAt = castToCalendar(e.getEndsAt());

                        boolean isBetween = (initialDate.compareTo(startsAt) > 0)
                                && (finalDate.compareTo(endsAt) < 0);
                        
                        return isBetween;})
                    .map(EventGetResponse::buildFromEntity)
                    .toList();
        }

        return new HttpList<>(
                HttpStatus.valueOf(200),
                events
        );
    }

    @Override
    public HttpEntity<EventGetResponse> createEvent(EventPayload payload) {
        var event = new Events();
        event.setStartsAt(formatDateFromString(payload.startsAt()));
        event.setEndsAt(formatDateFromString(payload.endsAt()));
        event.setDescription(payload.description());

        if (payload.disciplineId().isPresent()) {
            var discipline = disciplinesJPARepository.findById(payload.disciplineId().get())
                .orElseThrow(() -> 
                    new ApplicationException(404, "Discipline not found."));
            
            var role = userSession.getProfileRole();

            if (role == ProfileRole.Instructor) {
                var profile = profilesJPARepository.findById(
                        userSession.getProfileId()).get();
    
                if (!profile.getDisciplines().contains(discipline)) {
                    throw new ApplicationException(
                        403, 
                        "User does not have permission to create event outside their disciplines.");
                }
            }

            event.setDiscipline(discipline);

        } else if (payload.groupId().isPresent()) {
            var group = groupsJPARepository.findById(payload.disciplineId().get())
                .orElseThrow(() ->
                    new ApplicationException(404, "Group not found."));
            
            event.setGroup(group);

        } else {
            throw new ApplicationException(400, "Event must be bound either to a group or a discipline.");
        }

        var savedEvent = eventsJPARepository.save(event);

        return new HttpEntity<>(
                HttpStatus.valueOf(201),
                EventGetResponse.buildFromEntity(savedEvent)
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

    private Calendar castToCalendar(Date date) {
        var cal = GregorianCalendar.getInstance();
        cal.setTime(date);

        return cal;
    }
}
