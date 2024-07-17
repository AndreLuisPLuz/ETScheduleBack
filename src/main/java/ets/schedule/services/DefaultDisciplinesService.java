package ets.schedule.services;

import ets.schedule.Exceptions.NotFoundException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.DisciplinePayload;
import ets.schedule.interfaces.services.DisciplinesService;
import ets.schedule.models.Disciplines;
import ets.schedule.repositories.CoursesRepository;
import ets.schedule.repositories.DisciplinesRepository;
import ets.schedule.repositories.GroupsRepository;
import ets.schedule.repositories.UserJPARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import java.util.concurrent.CompletableFuture;

public class DefaultDisciplinesService implements DisciplinesService {

    @Autowired
    private DisciplinesRepository disciplinesRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UserJPARepository userRepository;

    @Override
    public CompletableFuture<HttpList<Disciplines>> getAllDisciplines() {
        return CompletableFuture.supplyAsync(() -> {

            return new HttpList<Disciplines>(
                    HttpStatusCode.valueOf(200),
                    disciplinesRepository.findAll()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Disciplines>> getDisciplineById(Long id) {
        var discipline = disciplinesRepository.findById(id);
//        if (discipline.isEmpty()) {
//            throw new NotFoundException("Discipline was not found.");
//        }

        return CompletableFuture.supplyAsync(() -> {
            return new HttpEntity<Disciplines>(
                    HttpStatusCode.valueOf(200),
                    discipline.get()
            );
        });
    }

    @Override
    public CompletableFuture<HttpEntity<Disciplines>> createDiscipline(DisciplinePayload obj) {
        var course = coursesRepository.findById(obj.courseId());
        var group = groupsRepository.findById(obj.groupId());
        var instructor = userRepository.findById(obj.instructorId());

//        if(course.isEmpty() || group.isEmpty() || instructor.isEmpty()) {
//            throw new NotFoundException("A resource was not found.");
//        }

        var newDiscipline = new Disciplines(obj.semester());
        newDiscipline.setCourse(course.get());
        newDiscipline.setGroup(group.get());
        newDiscipline.setInstructor(instructor.get());

        return CompletableFuture.supplyAsync(() -> {
            return new HttpEntity<Disciplines>(
                    HttpStatusCode.valueOf(201),
                    disciplinesRepository.save(newDiscipline)
            );
        });
    }
}
