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

//    @Query(value = "SELECT *" +
//            "FROM events e" +
//            "         INNER JOIN disciplines d" +
//            "                    ON e.discipline_id = d.id" +
//            "         INNER JOIN profiles p\n" +
//            "                    ON d.instructor_id = p.id" +
//            "WHERE MONTH(e.starts_at) = :month AND YEAR(e.starts_at) = :year AND p.id = :instructorId",
//            nativeQuery = true)
//    List<Events> findByInstructorAndDate(Integer month, Integer year, Long instructorId);

    @Query(value = "SELECT e.id AS event_id, e.description AS event_name, " +
            "d.id AS discipline_id, " +
            "p.id AS instructor_id " +
            "FROM events e " +
            "INNER JOIN disciplines d ON e.discipline_id = d.id " +
            "INNER JOIN profiles p ON d.instructor_id = p.id " +
            "WHERE MONTH(e.starts_at) = MONTH(:startsAt) " +
            "AND YEAR(e.starts_at) = YEAR(:startsAt) " +
            "AND p.id = :instructorId",
            nativeQuery = true)
    List<Events> findByInstructorAndDate(@Param("startsAt") String startsAt,
                                         @Param("instructorId") Long instructorId);
}
