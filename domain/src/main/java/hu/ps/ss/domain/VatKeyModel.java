package hu.ps.ss.domain;

import hu.ps.ss.domain.pojo.ItemWithIdEditable;
import java.math.BigDecimal;
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
public class VatKeyModel extends ItemWithIdEditable {

    private BigDecimal value;
    private int exemption;
    private String name;
}