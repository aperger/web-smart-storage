package hu.ps.ss.domain;


import hu.ps.ss.domain.pojo.ItemWithIdEditable;
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CurrencyModel extends ItemWithIdEditable {

	private String symbol;
	private String code;

}