package ets.schedule.repositories;

import ets.schedule.models.InstructorSkills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorSkillJPARepository extends JpaRepository<InstructorSkills, Long> {
}
