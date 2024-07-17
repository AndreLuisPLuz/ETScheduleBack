package ets.schedule.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "group")
    private List<Disciplines> disciplines;

    @OneToMany(mappedBy = "group")
    private List<Events> events;

    @OneToMany(mappedBy = "group")
    private List<Profiles> profiles;
}
