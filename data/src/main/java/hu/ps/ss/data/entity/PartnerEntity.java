package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * The persistent class for the TPARTNEREK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity()
@Table(name="TPARTNEREK")
@NamedQuery(name="Partner.findAll", query="SELECT p FROM PartnerEntity p ORDER BY p.name")
public class PartnerEntity extends EntityBase {

	@Column(name="FTIPUS")
	private int partnerType;
	
	@Column(name="FNEV")
	private String name;

	@Column(name="FORSZAG")
	private String country;

	@Column(name="FORSZAGKOD")
	private String countryCode;
	
	@Column(name="FIRSZ")
	private String areaCode;

	@Column(name="FTELEPULES")
	private String settlement;

	@Column(name="FKERULET")
	private String district;
	
	@Column(name="FKOZTER_JELLEG")
	private String publicSpaceNature;

	@Column(name="FKOZTER_NEV")
	private String publicSpaceName;

	@Column(name="FHAZSZAM")
	private String streetNumber;
	
	@Column(name="FEPULET")
	private String building;
	
	@Column(name="FLEPCSOHAZ")
	private String stairway;
	
	@Column(name="FSZINT")
	private String floor;
	
	@Column(name="FAJTO")
	private String door;

	@Column(name="FADOSZAM")
	private String taxNumber;

	@Column(name="FBANKSZLA")
	private String bankAccount;
	
	@Column(name="FEMAIL")
	private String email;
	
	@Column(name="FTELEFON")
	private String phoneNumber;
	
	@Column(name="FFAX")
	private String faxNumber;
	
	@Column(name="FDEFFIZHAT")
	private int defDedlineInDays;
	
	@Column(name="FDEFSZLAPELD")
	private int decInvoiceCopy;

	@Column(name="FDEFVALUTA")
	private int defCurrency;
	
	@Column(name="FKARTYASZAM")
	private String cardNumber;
	
	@Column(name="FERTESITESEK")
	private int notificationMask;

	// Not use field like under Delphi -> preference / FKEDVEZMENY
	// preferenece only used on items, preference field in the editor is only a helper to edit items;
	@Column(name="FKEDVEZMENY", precision = 12, scale = 2)
	private BigDecimal discount;

	@Column(name="FVEZETEKNEV")
	private String lastName;
	
	@Column(name="FKERESZTNEV")
	private String firstName;
	
	@Column(name="FNEM")
	private Character sex;
	
	@Column(name="FSZULDATUM")
	private LocalDate birthDate;

	//bi-directional many-to-one association to DocumentHeader
	@OneToMany(mappedBy="partner", fetch = FetchType.LAZY)
	private List<DocumentHeader> docHeaders;


	public DocumentHeader addDocHeader(DocumentHeader docHeader) {
		docHeaders.add(docHeader);
		docHeader.setPartner(this);
		return docHeader;
	}

	public DocumentHeader removeDocHeader(DocumentHeader docHeader) {
		docHeaders.remove(docHeader);
		docHeader.setPartner(null);
		return docHeader;
	}

}