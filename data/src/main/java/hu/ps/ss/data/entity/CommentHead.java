package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


/**
 * The persistent class for the TMEGJEGYZFEJ database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "TMEGJEGYZFEJ")
@Entity
@NamedQuery(name="CommentHead.findAll", query="SELECT ch FROM CommentHead ch ORDER BY ch.name")
public class CommentHead extends EntityBase {

	@Column(name="FNEV")
	private String name;

}