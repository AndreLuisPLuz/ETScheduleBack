package ets.schedule.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "groups")
public class Groups extends BaseModel {

    public Groups(String name, Date beginsAt, Date endsAt) {
        this.name = name;
        this.beginsAt = beginsAt;
        this.endsAt = endsAt;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "begins_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date beginsAt;

    @Column(name = "ends_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endsAt;
}
