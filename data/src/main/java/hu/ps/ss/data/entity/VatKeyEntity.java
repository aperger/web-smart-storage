package hu.ps.ss.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The persistent class for the TAFATIPUSOK database table.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TAFATIPUSOK")
@NamedQuery(name = "VatKey.findAll", query = "SELECT v FROM VatKeyEntity v ORDER BY v.name")
public class VatKeyEntity extends EntityBase {

  @Column(name = "FAFA", precision = 12, scale = 2)
  private BigDecimal value;

  @Column(name = "FMENTESSEG")
  private int exemption;

  @Column(name = "FNEV")
  private String name;

}