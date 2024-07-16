package ets.schedule.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ets.schedule.models.Courses;

public interface CourseRepository extends JpaRepository<Courses, Long> {
    public List<Courses> findByName(String courseName);
}
