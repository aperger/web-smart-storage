package hu.ps.ss.data.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;


/**
 * The persistent class for the TMEGJEGYZSOROK database table.
 * 
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "TMEGJEGYZSOROK")
@Entity
@NamedQuery(name="CommentLine.findAll", query="SELECT cl FROM CommentLine cl ORDER BY cl.id")
public class CommentLine extends EntityBase {

	@ManyToOne
	@JoinColumn(name="FMEGJEGYZFEJ")
	private CommentHead head;

	@Column(name="FSOR")
	private String line;

}