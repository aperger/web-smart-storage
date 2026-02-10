package hu.ps.ss.domain;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
public class ItemPropertyModel extends ItemWithIdEditable {

	private String name;
	private String description;
	private int propertyType;

}