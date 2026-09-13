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
    name = "Currency",
    description = "Currency data transfer object used by partner defaults, prices, and document calculations."
)
public class CurrencyDto extends ItemWithIdEditable {

  @Schema(
      description = "Currency symbol used in the legacy application.",
      example = "Ft",
      requiredMode = RequiredMode.NOT_REQUIRED,
      maxLength = 20
  )
  String symbol;

  @Schema(
      description = "Currency code, typically an ISO-style code such as HUF or EUR.",
      example = "HUF",
      requiredMode = RequiredMode.REQUIRED,
      minLength = 1,
      maxLength = 20
  )
  String code;
}
