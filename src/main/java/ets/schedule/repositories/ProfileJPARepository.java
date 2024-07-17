package ets.schedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ets.schedule.models.Profiles;

public interface ProfileJPARepository extends JpaRepository<Profiles, Long> {
    
}
