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
@Table(name = "competences")
public class Competences extends BaseModel {

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Disciplines discipline;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @OneToMany(mappedBy = "competence")
    private List<StudentCompetences> competences;

    public static Competences build(Disciplines discipline, String name, Float weight) {
        Competences competence = new Competences();
        competence.setDiscipline(discipline);
        competence.setName(name);
        competence.setWeight(weight);

        return competence;
    }
}
