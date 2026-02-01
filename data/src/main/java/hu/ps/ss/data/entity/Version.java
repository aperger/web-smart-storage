package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


/**
 * The persistent class for the TVERSION database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TVERSION")
@NamedQuery(name = "Version.findAll", query = "SELECT v FROM Version v")
public class Version {

    @Id()
    @Column(name = "FMAJOR")
    private int major;

    @Column(name = "FMINOR")
    private int minor;

}