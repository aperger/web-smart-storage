package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


/**
 * The persistent class for the TMOZGASTIPUSOK database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TMOZGASTIPUSOK")
@NamedQuery(name = "StockMovementType.findAll", query = "SELECT smt FROM StockMovementType smt ORDER BY smt.name")
public class StockMovementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FKOD")
    private int code;

    @Column(name = "FNEV")
    private String name;

}