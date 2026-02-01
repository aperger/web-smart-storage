package hu.ps.ss.data.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;


/**
 * The persistent class for the TTULAJDONSAGOK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TTULAJDONSAGOK")
@NamedQuery(name="ItemProperty.findAll", query="SELECT ip FROM ItemProperty ip ORDER BY ip.name")
public class ItemProperty extends EntityBase {

	@Column(name="FNEV")
	private String name;

	@Column(name ="FLEIRAS")
	private String description;

	@Column(name="FTIPUS")
	private int propertyType;

}