package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the TFIZMOD database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TFIZMOD")
@NamedQuery(name="PaymentMethod.findAll", query="SELECT pm FROM PaymentMethodEntity pm ORDER BY pm.name")
public class PaymentMethodEntity extends EntityBase {

	@Column(name="FNEV")
	private String name;

	@Column(name="FFIZHAT")
	private int dueDate;

	@Column(name="FSZLAFORMAT")
	private int invoiceFormat;
}