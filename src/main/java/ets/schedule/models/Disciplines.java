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

    @Column(name = "color_code", nullable = true)
    private String colorCode;

    @OneToMany(mappedBy = "discipline")
    private List<Competences> competences;

    @OneToMany(mappedBy = "discipline")
    private List<Events> events;

    @OneToMany(mappedBy = "discipline")
    private List<StudentAvaliation> studentAvaliations;

    public static Disciplines build(Groups group, Profiles instructor, Courses course, Integer semester) {
        Disciplines discipline = new Disciplines();
        discipline.setGroup(group);
        discipline.setInstructor(instructor);
        discipline.setCourse(course);
        discipline.setSemester(semester);

        return discipline;
    }
}
