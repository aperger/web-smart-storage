package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TAFATIPUSOK database table.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TAFATIPUSOK")
@NamedQuery(name = "VatKey.findAll", query = "SELECT v FROM VatKey v ORDER BY v.name")
public class VatKey extends EntityBase {

    @Column(name = "FAFA", precision = 12, scale = 2)
    private BigDecimal value;

    @Column(name = "FMENTESSEG")
    private int exemption;

    @Column(name = "FNEV")
    private String name;

    //bi-directional many-to-one association to DocumetItem
    @OneToMany(mappedBy = "vatKey", fetch = FetchType.LAZY)
    private List<DocumentItem> tbizonylattetels;


}