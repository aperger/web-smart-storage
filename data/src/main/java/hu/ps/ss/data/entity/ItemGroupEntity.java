package hu.ps.ss.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the TCSOPORTOK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name="TCSOPORTOK")
@Entity
@NamedQuery(name="ItemGroup.findAll", query="SELECT g FROM ItemGroupEntity g ORDER BY g.name")
public class ItemGroupEntity extends EntityBase {

	@Column(name="FNEV")
	private String name;

	@Column(name="FFUNKCIO")
	private int function;

	@Column(name="FLEIRAS")
	private String description;

	@OneToMany(mappedBy = "itemGroup")
	@JsonIgnore
	private List<ItemTypeEntity> itemTypes;

}