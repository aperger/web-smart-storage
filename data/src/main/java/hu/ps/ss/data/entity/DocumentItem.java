package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TBIZONYLATTETEL database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBIZONYLATTETEL")
@NamedQuery(name = "DocumentItem.findAll", query = "SELECT i FROM DocumentItem i ORDER BY i.id")
public class DocumentItem extends EntityBase {

    //bi-directional many-to-one association to DocumentHeader
    @ManyToOne
    @JoinColumn(name="FBIZONYLATFEJ")
    private DocumentHeader header;

    @Column(name="FTETELKOD")
    private String itemCode;

    @Column(name="FNEV")
    private String name;

    @Column(name="FITJSZ")
    private String customsCode;

    @Column(name="FMENNY")
    private BigDecimal amount;

    @Column(name="FEGYSEG")
    private String unit;

    @Column(name="FEGYSEGAR")
    private BigDecimal unitPrice;

    //bi-directional many-to-one association to VatKeyEntity
    @ManyToOne
    @JoinColumn(name="FAFAAZON", nullable = true)
    private VatKeyEntity vatKey;

    @Column(name="FAFA", precision = 12, scale = 2)
    private BigDecimal vatValue;

    @Column(name="FAFANEV")
    private String vatName;

    @Column(name="FKEDVEZMENY")
    private BigDecimal preference;

    @Column(name="FKOZVSZOLGALTATAS")
    private int mediatedService;

    @Column(name = "fanyagmozgas")
    private int storageMoveId;

    @Column(name="FVISSZAVET")
    private BigDecimal amountBack;

    //bi-directional many-to-one association to DocumentItemNote
    @OneToMany(mappedBy = "item")
    private List<DocumentItemNote> notes;

    public DocumentItemNote addNote(DocumentItemNote note) {
        notes.add(note);
        note.setItem(this);
        return note;
    }

    public DocumentItemNote removeNote(DocumentItemNote note) {
        notes.remove(note);
        note.setItem(null);
        return note;
    }

}
