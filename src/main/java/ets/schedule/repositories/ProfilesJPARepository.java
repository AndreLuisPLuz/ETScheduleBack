package ets.schedule.repositories;

import ets.schedule.models.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilesJPARepository extends JpaRepository<Profiles, Long> {
}
