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
    name = "Storage",
    description = "Storage or cost-centre data transfer object from the Basics menu."
)
public class StorageDto extends ItemWithIdEditable {

  @Schema(description = "Storage or cost-centre name.", example = "Main warehouse",
      requiredMode = RequiredMode.REQUIRED)
  String name;

  @Schema(description = "Legacy storage type discriminator: 0 = cost centre, 1 = storage.",
      example = "1", requiredMode = RequiredMode.REQUIRED)
  int type;
}
