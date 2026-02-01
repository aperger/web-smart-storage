package hu.ps.ss.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * The persistent class for the TAUTOBIZONYLAT database table.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TAUTOBIZONYLAT",
        uniqueConstraints = {
                @UniqueConstraint(name = "idx_AUTOBIZONYLAT_ID", columnNames = {"FSZLAVEVO", "FSZLAMINTA"}),
        },
        indexes = {
                @Index(name = "idx_AUTOBIZONYLAT_MINTA", columnList = "FSZLAMINTA")
        }
)
@NamedQuery(name = "AutoDocument.findAll", query = "SELECT ab FROM AutoDocument ab ORDER BY ab.id")
public class AutoDocument extends EntityBase {

    @Column(name = "FSZLAGENNEXT")
    private LocalDateTime nextInvoiceGeneration;

    @Column(name = "FSZLAGYAKORISAG")
    private int invoiceFrequency;

    @Column(name = "FSZLAHIVATKOZAS")
    private String referToInvoice;

    @Column(name = "FSZLAMEGRENDELES")
    private String invoiceOfOrder;

    @Column(name = "FSZLAMINTA")
    private int invoicePattern;

    @Column(name = "FSZLARENDSZAM")
    private String licencePlate;

    @Column(name = "FSZLAVEVO")
    private int customer;

}