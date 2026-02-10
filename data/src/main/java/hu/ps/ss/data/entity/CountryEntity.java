package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the TORSZAGOK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TORSZAGOK")
@NamedQuery(name="Country.findAll", query="SELECT c FROM CountryEntity c ORDER BY c.name")
public class CountryEntity extends EntityBase {

	@Column(name="FKOD")
	private String code;

	@Column(name="FNEV")
	private String name;

}