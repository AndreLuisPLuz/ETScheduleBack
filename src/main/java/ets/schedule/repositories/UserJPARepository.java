package ets.schedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ets.schedule.models.Users;
import java.util.List;


public interface UserJPARepository extends JpaRepository<Users, Long> {
    List<Users> findByUsername(String username);
}
