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
@Table(name = "competences")
public class Competences extends BaseModel {

    public Competences(String name, Float weight) {
        this.name = name;
        this.weight = weight;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Disciplines discipline;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Float weight;
}
