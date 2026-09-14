package hu.ps.ss.data.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

/**
 * The persistent class for the TRIPORTOK database table.
 * 
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TRIPORTOK")
@NamedQuery(name="Report.findAll", query="SELECT r FROM Report r ORDER BY r.name")
public class Report extends EntityBase {

	@Column(name="FNEV")
	private String name;

	@Lob // required  for MySQL
	@Column(name="FRIPORT")
	private byte[] reportTemplate;

	@Column(name="FTIPUS")
	private int templateType;

}