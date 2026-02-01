package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;


/**
 * The persistent class for the TMEGJEGYZSOROK database table.
 * 
 */
@Builder
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