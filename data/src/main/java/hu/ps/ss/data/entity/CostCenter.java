package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the TKOLTSEGHELYEK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "TKOLTSEGHELYEK")
@Entity
@NamedQuery(name="CostCenter.findAll", query="SELECT cc FROM CostCenter cc ORDER BY cc.name")
public class CostCenter extends EntityBase {

	@Column(name="FNEV")
	private String name;

	@Column(name="FTIPUS")
	private int type;

}