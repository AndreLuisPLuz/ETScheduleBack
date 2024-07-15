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
@Table(name = "instructor_skills")
public class InstructorSkills extends BaseModel {

    public InstructorSkills(String subject, Integer value) {
        this.subject = subject;
        this.value = value;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Users instructor;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "value", nullable = false)
    private Integer value;
}
