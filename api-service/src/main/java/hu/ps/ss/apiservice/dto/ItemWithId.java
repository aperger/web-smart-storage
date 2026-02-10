package hu.ps.ss.apiservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter(AccessLevel.PROTECTED)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    description = "Base DTO class for entities with a unique identifier"
)
public class ItemWithId {

  @Schema(
      description = "Unique identifier of the entity",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY,
      requiredMode = RequiredMode.NOT_REQUIRED
  )
  int id;
}
