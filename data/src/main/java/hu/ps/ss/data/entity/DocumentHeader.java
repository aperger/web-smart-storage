package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The persistent class for the TBIZONYLATFEJ database table.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBIZONYLATFEJ")
@NamedQuery(name = "DocumentHeader.findAll", query = "SELECT dh FROM DocumentHeader dh ORDER BY dh.id DESC")
public class DocumentHeader extends EntityBase {

    @Column(name="FSZEV") // számviteli év
    private int yearAccounting;

    @Column(name="FSZSZAM") // sorszám a számviteli éven belül
    private int docNumber;

    @Column(name="FSZSZAMELOTAG") // sorszám előtag
    private String docNumberPrefix;

    @Column(name="FBIZTIP")
    private int docType;

    @Column(name="FTIPUS")
    private int subType;

    @Column(name="FFORMATUM")
    private int docFormat;

    @Column(name="FSTATUS")
    private int docStatus;

    @Column(name="FKIALLDATUM")
    private LocalDateTime dateCreated;

    @Column(name="FFIZDATUM")
    private LocalDateTime dateDue; // datePrompt - fitetési határidő

    @Column(name="FTELJDATUM")
    private LocalDateTime dateFulfilment;

    @Column(name="FSTORNOKIALLDATUM")
    private LocalDateTime dateCancel;

    @Column(name="FSTORNOSZLA")
    private int docNumberCancel;

    @Column(name="FFIZMOD")
    private String paymentMethod;

    @Column(name="FVALUTA")
    private String currency;

    @Column(name="FVALUTAKOD")
    private String currencyCode;

    // Not use field like under Delphi -> preference / FKEDVEZMENY
    // preferenece only used on items, preference field in the editor is only a helper to edit items;
    @Column(name="FKEDVEZMENY", precision = 12, scale = 2)
    private BigDecimal exchangeRate;

    @Column(name="FADOSZABALY")
    private String taxRule;

    @Column(name="FHIVATKOZAS")
    private String reference;

    @Column(name="FIDOSZAKKEZDETE")
    private LocalDate datePeriodStart;

    @Column(name="FIDOSZAKVEGE")
    private LocalDate datePeriodEnd;

    @Column(name="FMEGRENDELES")
    private String orderId;

    @Column(name="FRENDSZAM")
    private String registrationId;

    @Column(name="FNYOMTATVA")
    private int printed;

    @Column(name="FPELDANY")
    private int copy;

    @Column(name="FPENZFORGALMIELSZ")
    private int cashAccounting;

    @Column(name="FSZAMVITELITELJESITES")
    private LocalDate dateAccountingFulfillment;

    @Column(name="FMEGJEGYZES")
    private String description;

    @Column(name="FCEGADOSZAM")
    private String companyTaxNumber;

    @Column(name="FCEGADOSZAMEU")
    private String companyTaxNumberEU;

    @Column(name="FCEGAJTO")
    private String companyDoor;

    @Column(name="FCEGBANKSZLA")
    private String companyBankAccount;

    @Column(name="FCEGEMAIL")
    private String companyEmail;

    @Column(name="FCEGEPULET")
    private String companyBuilding;

    @Column(name="FCEGFAX")
    private String companyFax;

    @Column(name="FCEGHAZSZAM")
    private String companyStreetNumber;

    @Column(name="FCEGIRSZ")
    private String companyAreaCode;

    @Column(name="FCEGKERULET")
    private String companyDistrict;

    @Column(name="FCEGKOZTER_JELLEG")
    private String companyPublicSpaceNature;

    @Column(name="FCEGKOZTER_NEV")
    private String companyPublicSpaceName;

    @Column(name="FCEGLEPCSOHAZ")
    private String companyStairWay;

    @Column(name="FCEGNEV")
    private String companyName;

    @Column(name="FCEGORSZAG")
    private String companyCountry;

    @Column(name="FCEGORSZAGKOD")
    private String companyCountryCode;

    @Column(name="FCEGSZINT")
    private String companyFloor;

    @Column(name="FCEGTEL")
    private String companyPhone;

    @Column(name="FCEGTELEP")
    private String companySettlement;

    @Column(name="FCEGVALLIGSZAM")
    private String companyRegistrationId;

    //bi-directional many-to-one association to Tpartnerek
    @ManyToOne
    @JoinColumn(name="FVEVOAZONOSITO", nullable = true)
    private Partner partner;

    @Column(name="FVEVONEV")
    private String customerName;

    @Column(name="FVEVOADOSZAM")
    private String customerTaxNumber;

    @Column(name="FVEVOADOSZAMEU")
    private String fvevoadoszameu;

    @Column(name="FVEVOBANKSZLA")
    private String customerBankAccount;

    @Column(name="FVEVOORSZAG")
    private String customerCountry;

    @Column(name="FVEVOORSZAGKOD")
    private String customerCountryCode;

    @Column(name="FVEVOIRSZ")
    private String customerAreaCode;

    @Column(name="FVEVOTELEPULES")
    private String customerSettlement;

    @Column(name="FVEVOEPULET")
    private String customerBuilding;

    @Column(name="FVEVOAJTO")
    private String customerDoor;

    @Column(name="FVEVOHAZSZAM")
    private String customerStreetNumber;

    @Column(name="FVEVOKERULET")
    private String customerDistrict;

    @Column(name="FVEVOKOZTER_JELLEG")
    private String customerPublicSpaceNature;

    @Column(name="FVEVOKOZTER_NEV")
    private String customerPublicSpaceName;

    @Column(name="FVEVOLEPCSOHAZ")
    private String customerStairWay;

    @Column(name="FVEVOSZINT")
    private String customerFloor;

    @Column(name="FVEVOTELEFON")
    private String customerPhone;

    @Column(name="FVEVOFAX")
    private String customerFax;

    @Column(name="FVEVOEMAIL")
    private String customerEmail;

    @Column(name="FSTR01")
    private String comment01;

    @Column(name="FSTR02")
    private String comment02;

    @Column(name="FSTR03")
    private String comment03;

    @Column(name="FSTR04")
    private String comment04;

    @Column(name="FSTR05")
    private String comment05;

    @Column(name="FSTR06")
    private String comment06;

    @Column(name="FSTR07")
    private String comment07;

    @Column(name="FSTR08")
    private String comment08;

    @Column(name="FSTR09")
    private String comment09;

    @Column(name="FSTR10")
    private String comment10;

    //bi-directional many-to-one association to Tbizonylattetel
    @OneToMany(mappedBy = "header")
    private List<DocumentItem> items;


    public DocumentItem addItem(DocumentItem item) {
        items.add(item);
        item.setHeader(this);
        return item;
    }

    public DocumentItem removeItem(DocumentItem item) {
        items.remove(item);
        item.setHeader(null);
        return item;
    }

}