package hu.ps.ss.apiservice.dto;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
    name = "VatKey",
    description = "VAT rate definition used for invoice tax calculations and Hungarian NAV integration."
)
public class VatKeyDto extends ItemWithIdEditable {

  @Schema(
      description = "VAT rate value, e.g. 27.0 for 27% VAT.",
      example = "27.00",
      requiredMode = RequiredMode.REQUIRED,
      minimum = "0",
      maximum = "1000"
  )
  BigDecimal value;

  @Schema(
      description = "VAT exemption flag. 0 means ordinary taxed rate, other values depend on the legacy application rules.",
      example = "0",
      requiredMode = RequiredMode.REQUIRED,
      minimum = "0",
      maximum = "1000"
  )
  int exemption;

  @Schema(
      description = "VAT key name, e.g. '27%' or 'AAM' depending on the legacy application conventions.",
      example = "27%",
      requiredMode = RequiredMode.REQUIRED,
      minLength = 1,
      maxLength = 255
  )
  String name;
}
