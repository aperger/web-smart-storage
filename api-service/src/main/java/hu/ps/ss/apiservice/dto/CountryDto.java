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
    name = "Country",
    description = "Country data transfer object used for address management and Hungarian online invoices. " +
                  "Contains country identification (code) and localized name."
)
public class CountryDto extends ItemWithIdEditable {

  @Schema(
      description = "Country code (ISO 3166-1 alpha-2 recommended, e.g., 'HU' for Hungary, 'DE' for Germany)",
      example = "HU",
      requiredMode = RequiredMode.REQUIRED,
      minLength = 2,
      maxLength = 10
  )
  String code;

  @Schema(
      description = "Country name in the local language or English",
      example = "Hungary",
      requiredMode = RequiredMode.REQUIRED,
      minLength = 1,
      maxLength = 255
  )
  String name;
}