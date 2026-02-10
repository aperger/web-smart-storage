package hu.ps.ss.data.entity;

import lombok.*;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The persistent class for the TTORZS database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="TTORZS")
@NamedQuery(name="MasterItem.findAll", query="SELECT mi FROM MasterItemEntity mi ORDER BY mi.name")
public class MasterItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "FRAKTKOD")
	private String stockCode;

	@Column(name="FNEV")
	private String name;

	@Column(name="FRENDKOD")
	private String orderNumber;

	@ManyToOne
	@JoinColumn(name = "FAFATIPUS")
	private VatKeyEntity vatKey;

	@ManyToOne
	@JoinColumn(name="FCSOPTIP")
	private ItemTypeEntity itemType;

	@Column(name="FEGYSEG")
	private String unit;

	/**
	 * TEÁOR (aka. ITJ)
	 */
	@Column(name="FITJ")
	private String activityCode;

	@Column(name="FKOZVSZOLGALTATAS")
	private int isDirectService;

	@Column(name="FBESZALLITO")
	private int supplier;

	@Column(name="FBESZIDO")
	private BigDecimal procurementTime;

	@Column(name="FDEFBESZAR")
	private BigDecimal defaultPurchasePrice;

	@Column(name="FDEFELADAR")
	private BigDecimal defaultSalePrice;

	@Column(name="FGYARTO")
	private int manufacturer;

	@Lob // required  for MySQL
	@Column(name="FIMAGE")
	private byte[] image;

	@Column(name="FLEIRAS")
	private String description;

	@Column(name="FMINKESZLET")
	private BigDecimal minimumStock;


	@Column(name="FSPECSZERSZAMVEVO")
	private int specToolCustomer;

	@Column(name = "FUSER")
	private String user;

	@Column(name="FIDO")
	private LocalDateTime modified;

	@OneToMany(mappedBy = "masterItem")
	private List<ItemPropertyValueEntity> propertyValues;
}