package hu.ps.ss.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The persistent class for the TBEALLIT database table.
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBEALLIT")
@NamedQuery(name = "Setting.findAll", query = "SELECT s FROM Setting s")
public class Setting {

    @Column(name = "FCEGNEV")
    private String companyName;

    @Column(name = "FEGYENIVALLALKOZO")
    private int isSelfEmployee;

    @Id()
    @Column(name = "FCEGADOSZAM")
    private String companyTaxNumber;

    @Column(name = "FCEGADOSZAMEU")
    private String companyTaxNumberEU;

    @Column(name = "FCEGVALLIGSZAM")
    private String registrationNumber;

    @Column(name = "FCEGORSZAG")
    private String companyCountry;

    @Column(name = "FCEGORSZAGKOD")
    private String companyCountryCode;

    @Column(name = "FCEGIRSZ")
    private String companyPostalCode;

    @Column(name = "FCEGTELEP")
    private String companySettlement;

    @Column(name = "FKERULET")
    private String district;

    @Column(name = "FKOZTER_JELLEG")
    private String publicSpaceType;

    @Column(name = "FKOZTER_NEV")
    private String publicSpaceName;

    @Column(name = "FHAZSZAM")
    private String houseNumber;

    @Column(name = "FEPULET")
    private String building;

    @Column(name = "FLEPCSOHAZ")
    private String stairway;

    @Column(name = "FSZINT")
    private String floor;

    @Column(name = "FAJTO")
    private String door;

    @Column(name = "FCEGEMAIL")
    private String companyEmail;

    @Column(name = "FCEGTEL")
    private String companyPhone;

    @Column(name = "FCEGFAX")
    private String companyFax;

    @Column(name = "FCEGBANKSZLA")
    private String companyBankAccount;

    @Column(name = "FCEGDEVIZASZLA1")
    private String companyCurrencyAccount1;

    @Column(name = "FCEGDEVIZASZLA2")
    private String companyCurrencyAccount2;

    @Column(name = "FCEGDEVIZASZLA3")
    private String companyCurrencyAccount3;

    @Column(name = "FDEFARRES")
    private BigDecimal defaultMargin;

    @Column(name = "FDEFFIZHATARIDO")
    private int defaultPaymentDeadline;

    @Column(name = "FDEFFIZMOD")
    private String defaultPaymentMethod;

    @Column(name = "FDEFKTGH")
    private int defaultCostCenter;

    @Column(name = "FDEFRAKTAR")
    private int defaultStorage;

    @Column(name = "FDEFSZAMLAMEGJEGYZ")
    private String defaultInvoiceComment;

    @Column(name = "FDEFVALUTA")
    private int defaultCurrency;

    @Lob // required  for MySQL
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "FSZLAEMBLEMA")
    @JsonIgnore
    private byte[] invoiceLogo;

    @Column(name = "FSZLAPELDANY")
    private int defaultNumberOfCopy;

    @Column(name = "FIDO")
    private LocalDateTime modified;

    @Column(name = "FUSER", length = 70)
    private String modifiedBy;
}