package ets.schedule.repositories;

import ets.schedule.models.StudentAvaliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAvaliationJPARepository extends JpaRepository<StudentAvaliation, Long> {
    List<StudentAvaliation> findByDisciplineIdAndStudent_Id(Long disciplineId, Long studentId);
}
