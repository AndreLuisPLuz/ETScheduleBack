package ets.schedule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disciplines")
public class Disciplines extends BaseModel {

    public Disciplines(Integer semester) {
        this.semester = semester;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Profiles instructor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @OneToMany(mappedBy = "discipline")
    private List<Competences> competences;

    @OneToMany(mappedBy = "discipline")
    private List<Events> events;

    @OneToMany(mappedBy = "discipline")
    private List<StudentAvaliation> studentAvaliations;
}
