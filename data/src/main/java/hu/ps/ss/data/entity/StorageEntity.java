package hu.ps.ss.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the TKOLTSEGHELYEK database table used for storage and cost centre
 * records in the legacy application.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "TKOLTSEGHELYEK")
@Entity
@NamedQuery(name = "Storage.findAll", query = "SELECT s FROM StorageEntity s ORDER BY s.name")
public class StorageEntity extends EntityBase {

  @Column(name = "FNEV")
  private String name;

  @Column(name = "FTIPUS")
  private int type;
}
