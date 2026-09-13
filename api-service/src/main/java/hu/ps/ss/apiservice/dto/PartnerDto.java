package hu.ps.ss.apiservice.dto;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(
    name = "Partner",
    description = "Partner data transfer object used for buyers, suppliers, and mixed partners."
)
public class PartnerDto extends ItemWithIdEditable {

  @Schema(description = "Legacy partner type discriminator.", example = "2",
      requiredMode = RequiredMode.REQUIRED)
  int partnerType;
  @Schema(description = "Partner display name.", example = "Example Ltd.",
      requiredMode = RequiredMode.REQUIRED)
  String name;
  @Schema(description = "Country name.", example = "Magyarorszag")
  String country;
  @Schema(description = "Country code.", example = "HU")
  String countryCode;
  @Schema(description = "Postal code.", example = "1111")
  String areaCode;
  @Schema(description = "Settlement or town.", example = "Budapest")
  String settlement;
  @Schema(description = "District.", example = "XI")
  String district;
  @Schema(description = "Public space type.", example = "utca")
  String publicSpaceNature;
  @Schema(description = "Public space name.", example = "Fo")
  String publicSpaceName;
  @Schema(description = "Street number.", example = "1")
  String streetNumber;
  @Schema(description = "Building.", example = "A")
  String building;
  @Schema(description = "Stairway.", example = "2")
  String stairway;
  @Schema(description = "Floor.", example = "3")
  String floor;
  @Schema(description = "Door.", example = "12")
  String door;
  @Schema(description = "Tax number.", example = "12345678-1-12")
  String taxNumber;
  @Schema(description = "Bank account.", example = "11700000-00000000-00000000")
  String bankAccount;
  @Schema(description = "Email address.", example = "info@example.com")
  String email;
  @Schema(description = "Phone number.", example = "+3612345678")
  String phoneNumber;
  @Schema(description = "Fax number.", example = "+3612345679")
  String faxNumber;
  @Schema(description = "Default deadline in days.", example = "8")
  int defDedlineInDays;
  @Schema(description = "Default invoice copy count.", example = "1")
  int decInvoiceCopy;
  @Schema(description = "Default currency id.", example = "1")
  int defCurrency;
  @Schema(description = "Card number.", example = "1234")
  String cardNumber;
  @Schema(description = "Notification bitmask.", example = "0")
  int notificationMask;
  @Schema(description = "Discount percentage or amount according to legacy semantics.", example = "0.00")
  BigDecimal discount;
  @Schema(description = "Last name.", example = "Doe")
  String lastName;
  @Schema(description = "First name.", example = "John")
  String firstName;
  @Schema(description = "Sex marker.", example = "M")
  Character sex;
  @Schema(description = "Birth date.", example = "1990-01-01")
  LocalDate birthDate;

  @Schema(description = "Electronic data service flag from the legacy partner record.", example = "1")
  int electronicDataService;
}
