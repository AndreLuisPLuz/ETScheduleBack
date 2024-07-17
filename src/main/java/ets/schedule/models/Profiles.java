package ets.schedule.models;

import ets.schedule.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profiles extends BaseModel {

    public Profiles(String role, Users user)
        throws
            IllegalArgumentException {
        this.role = ProfileRole.getRole(role);
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "group_id", nullable = true)
    private Groups group;

    @Column(name = "role", nullable = false)
    private ProfileRole role;

    @Column(name = "consensus", nullable = true)
    private String consensus;

    @OneToMany(mappedBy = "instructor")
    public List<Disciplines> disciplines;

    @OneToMany(mappedBy = "instructor")
    public List<InstructorSkills> instructorSkills;

    @OneToMany(mappedBy = "instructor")
    public List<StudentAvaliation> studentAvaliations;
}
