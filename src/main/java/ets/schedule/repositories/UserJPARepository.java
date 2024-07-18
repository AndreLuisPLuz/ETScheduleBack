package ets.schedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ets.schedule.enums.ProfileRole;
import ets.schedule.models.Users;
import java.util.List;
import java.util.Optional;


public interface UserJPARepository extends JpaRepository<Users, Long> {
    List<Users> findByUsername(String username);

    @Query(value = "SELECT u FROM Profiles p INNER JOIN p.user u WHERE p.id = :profileId")
    Optional<Users> findByProfileId(Long profileId);

    @Query(value = "SELECT u FROM Profiles p INNER JOIN p.user u WHERE p.role = :role")
    List<Users> findByRole(ProfileRole role);
}
