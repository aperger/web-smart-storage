package hu.ps.ss.apiservice.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MasterItemDto extends ItemEditable {

  String stockCode;
  String name;
  String orderNumber;
  int vatKeyId;
  int itemTypeId;
  String unit;
  String activityCode;
  int isDirectService;
  int supplier;
  BigDecimal procurementTime;
  BigDecimal defaultPurchasePrice;
  BigDecimal defaultSalePrice;
  int manufacturer;
  String description;
  BigDecimal minimumStock;
  int specToolCustomer;
  List<ItemPropertyValueDto> propertyValues;
}
