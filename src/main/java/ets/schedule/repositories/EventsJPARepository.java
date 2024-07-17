package ets.schedule.repositories;

import ets.schedule.models.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsJPARepository extends JpaRepository<Events, Long> {
}
