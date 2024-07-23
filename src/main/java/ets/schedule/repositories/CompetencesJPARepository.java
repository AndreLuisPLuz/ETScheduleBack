package ets.schedule.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ets.schedule.models.Competences;

public interface CompetencesJPARepository extends JpaRepository<Competences, Long>{
    public List<Competences> findByName(String competenceName);
}
