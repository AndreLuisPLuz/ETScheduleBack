package ets.schedule.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Events extends BaseModel {

    public Events(Date startsAt, Date endsAt, String description) {
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.description = description;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_id", nullable = true)
    private Disciplines discipline;

    @Column(name = "starts_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startsAt;

    @Column(name = "ends_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsAt;

    @Column(name = "description", nullable = false)
    private String description;
}
