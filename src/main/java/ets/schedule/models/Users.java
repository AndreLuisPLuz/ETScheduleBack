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
@Table(name = "users")
public class Users extends BaseModel {
    
    public Users(String username, Date birthDate, String fullName, String password) {
        this.username = username;
        this.birthDate = birthDate;
        this.fullName = fullName;
        this.password = password;
    }

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date_of_birth", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;
}
