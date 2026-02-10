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
@NamedQuery(name="ItemPropertyValue.findAll", query="SELECT ipv FROM ItemPropertyValueEntity ipv ORDER BY ipv.id")
public class ItemPropertyValueEntity extends EntityBase {

	@ManyToOne
	@JoinColumn(name="FTULAJDONSAG")
	private ItemPropertyEntity itemProperty;

	@ManyToOne
	@JoinColumn(name="FRAKTKOD")
	private MasterItemEntity masterItem;

	@Column(name="FDOUBLE")
	private BigDecimal numericValue;

	@Column(name="FSTRING")
	private String textValue;

}