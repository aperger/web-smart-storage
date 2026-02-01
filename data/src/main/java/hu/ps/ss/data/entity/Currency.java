package hu.ps.ss.data.entity;

import jakarta.persistence.*;


/**
 * The persistent class for the TVALUTAK database table.
 * 
 */
@Entity
@Table(name="TVALUTAK")
@NamedQuery(name="Currency.findAll", query="SELECT c FROM Currency c ORDER BY c.fkod")
public class Currency extends EntityBase {

	@Column(name="FJEL")
	private String fjel;

	@Column(name="FKOD")
	private String fkod;

}