package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * The persistent class for the TUSERS database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TUSERS")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM UserEntity u ORDER BY u.fuserid")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FUSERID")
    private String fuserid;

    @Column(name = "FPASSWORD")
    private String password;

    @Column(name = "FSO")
    private String salt;

    @Column(name = "FTIPUS")
    private String userRoles;

    @Column(name = "FUSER")
    private String modofiedBy;

    @Column(name = "FIDO")
    private LocalDateTime modofied;
}