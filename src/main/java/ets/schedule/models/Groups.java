package ets.schedule.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    public static Groups build(String name, Date beginsAt, Date endsAt) {
        Groups group = new Groups();
        group.setName(name);
        group.setBeginsAt(beginsAt);
        group.setEndsAt(endsAt);

        return group;
    }
}
