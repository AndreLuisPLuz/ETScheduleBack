package ets.schedule.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ets.schedule.models.Profiles;
import ets.schedule.models.Users;

public interface ProfilesJPARepository extends JpaRepository<Profiles, Long> {
    List<Profiles> findByUser(Users user);
    Profiles findByUser_Id(Long id);
}
