package hu.ps.ss.data.entity;

import jakarta.persistence.*;
import lombok.*;


/**
 * The persistent class for the TVALUTAK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TVALUTAK")
@NamedQuery(name="Currency.findAll", query="SELECT c FROM CurrencyEntity c ORDER BY c.fkod")
public class CurrencyEntity extends EntityBase {

	@Column(name="FJEL")
	private String fjel;

	@Column(name="FKOD")
	private String fkod;

}