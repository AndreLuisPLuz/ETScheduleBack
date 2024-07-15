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
@Table(name = "profile")
public class Profiles extends BaseModel {

    public Profiles(String role, String consensus) {
        this.role = role;
        this.consensus = consensus;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "consensus", nullable = true)
    private String consensus;
}
