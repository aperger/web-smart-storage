package hu.ps.ss.apiservice.dto;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(
    name = "ItemType",
    description = "Item type data transfer object under an item group."
)
public class ItemTypeDto extends ItemWithIdEditable {

  @Schema(description = "Parent item group identifier.", example = "1",
      requiredMode = RequiredMode.REQUIRED)
  int itemGroupId;

  @Schema(description = "Optional item type description.", example = "Consulting")
  String description;

  @Schema(description = "Item type name.", example = "Service", requiredMode = RequiredMode.REQUIRED)
  String name;
}
