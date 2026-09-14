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
    name = "ItemGroup",
    description = "Item group data transfer object used to classify items and services."
)
public class ItemGroupDto extends ItemWithIdEditable {

  @Schema(description = "Group name.", example = "Services", requiredMode = RequiredMode.REQUIRED)
  String name;

  @Schema(description = "Legacy function discriminator for the group.", example = "1",
      requiredMode = RequiredMode.REQUIRED)
  int function;

  @Schema(description = "Optional group description.", example = "Service related items")
  String description;
}
