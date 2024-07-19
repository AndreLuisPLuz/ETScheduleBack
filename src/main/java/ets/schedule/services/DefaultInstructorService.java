package ets.schedule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.data.HttpList;
import ets.schedule.data.responses.instructor.InstructorResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.InstructorService;
import ets.schedule.repositories.ProfileJPARepository;

public class DefaultInstructorService implements InstructorService {
    @Autowired
    ProfileJPARepository repo;

    @Override
    public HttpList<InstructorResponse> getAllInstructors() {
        var allInstructors = repo.findAll()
            .stream()
            .filter(p -> p.getRole() == ProfileRole.Instructor)
            .toList();

        return new HttpList<>(
            HttpStatusCode.valueOf(200),
            allInstructors.stream()
                .map(InstructorResponse::buildFromEntity)
                .toList());
    }
}
