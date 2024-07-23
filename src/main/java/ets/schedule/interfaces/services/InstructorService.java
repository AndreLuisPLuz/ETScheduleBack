package ets.schedule.interfaces.services;

import ets.schedule.data.HttpList;
import ets.schedule.data.responses.instructor.InstructorResponse;

public interface InstructorService {
    HttpList<InstructorResponse> getAllInstructors();
}
