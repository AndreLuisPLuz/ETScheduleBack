package ets.schedule.repositories;

import ets.schedule.models.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventsJPARepository extends JpaRepository<Events, Long> {
    @Query(value = "SELECT * FROM events WHERE MONTH(starts_at) = :month AND YEAR(starts_at) = :year AND group_id = :groupId", nativeQuery = true)
    List<Events> findByMonthAndYear(@Param("month") Integer month,
                                    @Param("year") Integer year,
                                    @Param("groupId") Long groupId);

    @Query(value = "SELECT e FROM Events e INNER JOIN e.discipline d INNER JOIN d.instructor i WHERE i.id = :instructorId")
    List<Events> findByInstructorId(Long instructorId);

    @Query(value = "SELECT e FROM Events e INNER JOIN e.group g WHERE g.id = :groupId")
    List<Events> findByGroupId(Long groupId);
}
