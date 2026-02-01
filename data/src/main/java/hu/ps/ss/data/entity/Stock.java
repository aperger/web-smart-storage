package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the TKESZLET database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TKESZLET")
@NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s ORDER BY s.id")
public class Stock {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StockPK id;

    @Column(name = "FERTEK", precision = 12, scale = 2)
    private BigDecimal value;

    @Column(name = "FMENNYISEG", precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "FIDO")
    private LocalDateTime modified;

    @Column(name = "FUSER", length = 70)
    private String modifiedBy;
}