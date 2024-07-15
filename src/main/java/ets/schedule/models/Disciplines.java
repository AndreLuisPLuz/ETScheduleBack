package ets.schedule.models;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

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
@Table(name = "disciplines")
public class Disciplines extends BaseModel {

    public Disciplines(Integer semester) {
        this.semester = semester;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups groupId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Users instructorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses courseId;

    @Column(name = "semester", nullable = false)
    private Integer semester;
}
