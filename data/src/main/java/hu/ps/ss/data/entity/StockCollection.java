package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TRAKTGYUJTO database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TRAKTGYUJTO")
@NamedQuery(name = "StockCollection.findAll", query = "SELECT sc FROM StockCollection sc ORDER BY sc.id DESC")
public class StockCollection {

    @EmbeddedId
    private StockCollectionPK id;

    @Column(name = "FMENNYISEG")
    private BigDecimal fmennyiseg;


}