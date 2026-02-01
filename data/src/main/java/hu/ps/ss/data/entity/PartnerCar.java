package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


/**
 * The persistent class for the TPARTNERAUTO database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="TPARTNERAUTO")
@NamedQuery(name="PartnerCar.findAll", query="SELECT pc FROM PartnerCar pc ORDER BY pc.id DESC")
public class PartnerCar {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FAZONOSITO")
	private int id;

	@Column(name="FALVAZSZAM")
	private String chassisBumber;

	@Column(name="FGYARTASIEV")
	private int yearOfManufacture;

	@Column(name="FMOTORSZAM")
	private String engineNumber;

	@Column(name="FPARTNER")
	private int partner;

	@Column(name="FRENDSZAM")
	private String licensePlateNumber;

	@Column(name="FGYARTMANY")
	private String product;

	@Column(name="FTIPUS")
	private String type;

	@Column(name="FVASARLASEVE")
	private int yearOfPurchase;

	@Column(name="FVASARLASMOD")
	private int typeOfPurchase;

}