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
@Table(name = "students_avaliation")
public class StudentAvaliation extends BaseModel {

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Disciplines discipline;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Profiles instructor;

    @Column(name = "comment", nullable = false)
    private String comment;

    public static StudentAvaliation build(Disciplines discipline, Profiles instructor, String comment) {
        StudentAvaliation studentAvaliation = new StudentAvaliation();
        studentAvaliation.setDiscipline(discipline);
        studentAvaliation.setInstructor(instructor);
        studentAvaliation.setComment(comment);

        return studentAvaliation;
    }
}
