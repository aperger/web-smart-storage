package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the TKESZLET database table.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StockPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "FKTGH")
    private String fktgh;

    @Column(name = "FANYAGKOD")
    private String fanyagkod;
}