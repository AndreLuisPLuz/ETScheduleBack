package ets.schedule.repositories;

import ets.schedule.models.StudentCompetences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCompetencesJPARepository extends JpaRepository<StudentCompetences, Long> {
}
