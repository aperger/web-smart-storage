package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the TRAKTGYUJTO database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class StockCollectionPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "FGYUJTOKOD")
    private String collectionCode;

    @Column(name = "FTETELKOD")
    private String itemCode;

}