package hu.ps.ss.apiservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor()
@Schema(
    description = "Base DTO class for entities with a unique identifier and audit trail information. " +
                  "Combines entity identification with modification tracking for complete entity metadata."
)
public class ItemWithIdEditable extends ItemWithId {

  @Schema(
      description = "Timestamp when the entity was last modified",
      example = "2026-02-10T14:30:00",
      accessMode = Schema.AccessMode.READ_ONLY,
			requiredMode = RequiredMode.AUTO
  )
  LocalDateTime modified;

  @Schema(
      description = "Username or identifier of the user who last modified the entity",
      example = "john.doe@example.com",
      accessMode = Schema.AccessMode.READ_ONLY,
      maxLength = 255,
			requiredMode = RequiredMode.AUTO
  )
  String modifiedBy;
}
