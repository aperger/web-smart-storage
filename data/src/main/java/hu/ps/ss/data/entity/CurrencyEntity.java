package hu.ps.ss.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * The persistent class for the TVALUTAK database table.
 * 
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TVALUTAK")
@NamedQuery(name="Currency.findAll", query="SELECT c FROM CurrencyEntity c ORDER BY c.code")
public class CurrencyEntity extends EntityBase {

	@Column(name="FJEL")
	private String symbol;

	@Column(name="FKOD")
	private String code;

}