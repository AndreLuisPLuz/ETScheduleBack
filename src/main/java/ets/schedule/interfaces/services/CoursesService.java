package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CoursePayload;
import ets.schedule.data.responses.get.CourseGetResponse;

public interface CoursesService {
    public HttpList<CourseGetResponse> getAll();
    public HttpEntity<CourseGetResponse> createCourse(CoursePayload payload);
    public HttpEntity<CourseGetResponse> editCourse(Long id, CoursePayload payload);
}
