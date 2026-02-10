package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;


/**
 * The persistent class for the TTIPUSOK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TTIPUSOK")
@NamedQuery(name="ItemType.findAll", query="SELECT t FROM ItemTypeEntity t ORDER BY t.name")
public class ItemTypeEntity extends EntityBase {

	@ManyToOne
	@JoinColumn(name = "FCSOPORT")
	private ItemGroupEntity itemGroup;

	@Column(name="FLEIRAS")
	private String description;

	@Column(name="FNEV")
	private String name;



}