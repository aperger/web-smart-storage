package hu.ps.ss.domain;

import hu.ps.ss.domain.pojo.ItemEditable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the TTORZS database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MasterItemModel extends ItemEditable {

	private String stockCode;
	private String name;
	private String orderNumber;
	private int vatKeyId;
	private int itemTypeId;
	private String unit;
	private String activityCode;
	private int isDirectService;
	private int supplier;
	private BigDecimal procurementTime;
	private BigDecimal defaultPurchasePrice;
	private BigDecimal defaultSalePrice;
	private int manufacturer;
	private String description;
	private BigDecimal minimumStock;
	private int specToolCustomer;

	private List<ItemPropertyValueModel> propertyValues;
}