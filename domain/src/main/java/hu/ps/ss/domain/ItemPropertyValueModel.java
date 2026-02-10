package hu.ps.ss.domain;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
public class ItemPropertyValueModel extends ItemWithIdEditable {
	private int  itemPropertyId;
	private String masterItemCode;
	private BigDecimal numericValue;
	private String textValue;
}