package hu.ps.ss.apiservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
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
    description = "Base DTO class for entities with audit trail information (modification tracking)"
)
public class ItemEditable {

  @Schema(
      description = "Timestamp when the entity was last modified",
      example = "2026-02-10T14:30:00",
      accessMode = Schema.AccessMode.READ_ONLY,
      requiredMode = RequiredMode.AUTO
  )
  private LocalDateTime modified;

  @Schema(
      description = "Username or identifier of the user who last modified the entity",
      example = "john.doe@example.com",
      accessMode = Schema.AccessMode.READ_ONLY,
      requiredMode = RequiredMode.AUTO,
      maxLength = 255
  )
  private String modifiedBy;

}
