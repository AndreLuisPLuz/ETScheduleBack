package ets.schedule.repositories;

import ets.schedule.models.Disciplines;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinesJPARepository extends JpaRepository<Disciplines, Long> {
    List<Disciplines> findDisciplinesByInstructor_Id(Long id);
    List<Disciplines> findDisciplinesByGroup_Id(Long id);
}
