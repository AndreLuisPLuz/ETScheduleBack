package ets.schedule.repositories;

import ets.schedule.models.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsJPARepository extends JpaRepository<Groups, Long> {
}
