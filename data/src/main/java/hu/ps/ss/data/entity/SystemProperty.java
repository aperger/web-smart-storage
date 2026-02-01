package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TRENDSZERTULAJDONSAGOK database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="TRENDSZERTULAJDONSAGOK")
@NamedQuery(name="SystemProperty.findAll", query="SELECT sp FROM SystemProperty sp ORDER BY sp.code")
public class SystemProperty {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "fkod")
	private String code;

	@Column(name = "fnev")
	private String name;

	@Column(name = "fszam", precision = 19, scale = 2)
	private BigDecimal numberValue;

	@Column(name = "fszoveg")
	private String textValue;

	@Column(name = "ftipus")
	private int valueType;

}