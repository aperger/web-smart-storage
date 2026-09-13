package hu.ps.ss.apiservice.dto;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(
    name = "ItemPropertyValue",
    description = "Property value assigned to a master item."
)
public class ItemPropertyValueDto extends ItemWithIdEditable {

  @Schema(description = "Referenced item property identifier.", example = "1")
  int itemPropertyId;

  @Schema(description = "Referenced master item stock code.", example = "ABC-001")
  String masterItemCode;

  @Schema(description = "Numeric property value when the property type is numeric.", example = "12.50")
  BigDecimal numericValue;

  @Schema(description = "Text property value when the property type is textual.", example = "Blue")
  String textValue;
}
