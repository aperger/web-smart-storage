package hu.ps.ss.apiservice.dto;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
    name = "ItemProperty",
    description = "Item property definition used for special item properties."
)
public class ItemPropertyDto extends ItemWithIdEditable {

  @Schema(description = "Property name.", example = "Serial number",
      requiredMode = RequiredMode.REQUIRED)
  String name;

  @Schema(description = "Optional property description.", example = "Optional external identifier")
  String description;

  @Schema(description = "Legacy property type discriminator.", example = "1",
      requiredMode = RequiredMode.REQUIRED)
  int propertyType;
}
