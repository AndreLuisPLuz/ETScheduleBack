package ets.schedule.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student_competences")
public class StudentCompetences extends BaseModel {

    @ManyToOne(optional = false)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competences competence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Profiles student;

    @Column(name = "degree", nullable = false)
    private String degree;

    public static StudentCompetences build(Competences competence, Profiles student, String degree) {
        StudentCompetences studentCompetences = new StudentCompetences();
        studentCompetences.setCompetence(competence);
        studentCompetences.setStudent(student);
        studentCompetences.setDegree(degree);

        return studentCompetences;
    }
}
