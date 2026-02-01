package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TTULAJERTEK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TTULAJERTEK")
@NamedQuery(name="ItemPropertyValue.findAll", query="SELECT ipv FROM ItemPropertyValue ipv ORDER BY ipv.id")
public class ItemPropertyValue extends EntityBase {

	@ManyToOne
	@JoinColumn(name="FTULAJDONSAG")
	private ItemProperty itemProperty;

	@ManyToOne
	@JoinColumn(name="FRAKTKOD")
	private MasterItem masterItem;

	@Column(name="FDOUBLE")
	private BigDecimal numericValue;

	@Column(name="FSTRING")
	private String textValue;

}