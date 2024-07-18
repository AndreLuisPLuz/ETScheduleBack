package ets.schedule.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Courses extends BaseModel {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "course")
    private Set<Disciplines> disciplines;

    public static Courses build(String name, String description) {
        Courses course = new Courses();
        course.setName(name);
        course.setDescription(description);

        return course;
    }
}
