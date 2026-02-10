package hu.ps.ss.domain;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PartnerModel extends ItemWithIdEditable {

  private int partnerType;
  private String name;
  private String country;
  private String countryCode;
  private String areaCode;
  private String settlement;
  private String district;
  private String publicSpaceNature;
  private String publicSpaceName;
  private String streetNumber;
  private String building;
  private String stairway;
  private String floor;
  private String door;
  private String taxNumber;
  private String bankAccount;
  private String email;
  private String phoneNumber;
  private String faxNumber;
  private int defDedlineInDays;
  private int decInvoiceCopy;
  private int defCurrency;
  private String cardNumber;
  private int notificationMask;
  private BigDecimal discount;


  private String lastName;
  private String firstName;
  private Character sex;
    private LocalDate birthDate;

}
