package ets.schedule.interfaces.services;

import java.util.concurrent.CompletableFuture;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.courses.CoursePayload;
import ets.schedule.data.responses.get.CourseGetResponse;
import ets.schedule.models.Courses;

public interface CoursesService {
    public HttpList<CourseGetResponse> getAll();
    public HttpEntity<CourseGetResponse> createCourse(CoursePayload payload);
    public HttpEntity<CourseGetResponse> editCourse(Long id, CoursePayload payload);
}
