package ets.schedule.repositories;

import ets.schedule.models.Disciplines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinesRepository extends JpaRepository<Disciplines, Long> {
}
