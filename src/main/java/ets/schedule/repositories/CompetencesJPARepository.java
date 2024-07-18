package ets.schedule.repositories;

import ets.schedule.models.Competences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetencesJPARepository extends JpaRepository<Competences, Long> {
}
