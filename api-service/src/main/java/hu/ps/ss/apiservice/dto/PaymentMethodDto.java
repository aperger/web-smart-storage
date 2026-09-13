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
    name = "PaymentMethod",
    description = "Payment method data transfer object used by documents and partner defaults."
)
public class PaymentMethodDto extends ItemWithIdEditable {

  @Schema(description = "Payment method name.", example = "Transfer",
      requiredMode = RequiredMode.REQUIRED)
  String name;

  @Schema(description = "Due date in days from document issue date.", example = "8",
      requiredMode = RequiredMode.REQUIRED)
  int dueDate;

  @Schema(description = "Legacy invoice or report format identifier.", example = "1",
      requiredMode = RequiredMode.REQUIRED)
  int invoiceFormat;
}
