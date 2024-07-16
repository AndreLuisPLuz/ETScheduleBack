package ets.schedule.interfaces.services;

import java.util.concurrent.CompletableFuture;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.HttpList;
import ets.schedule.data.payloads.CourseCreatePayload;
import ets.schedule.models.Courses;

public interface CourseService {
    public CompletableFuture<HttpList<Courses>> getAll(); 
    public CompletableFuture<HttpEntity<Courses>> createCourse(CourseCreatePayload payload); 
    public CompletableFuture<HttpEntity<Courses>> editCourse(Long id); 
}
