package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.*;


/**
 * The persistent class for the TBIZTETELMEGJEGYZ database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="TBIZTETELMEGJEGYZ")
@NamedQuery(name="DocumentItemNote.findAll", query="SELECT d FROM DocumentItemNote d ORDER BY d.id")
public class DocumentItemNote extends EntityBase {

	@ManyToOne
	@JoinColumn(name="FBIZONYLATTETEL")
	private DocumentItem item;

	@Column(name="FSOR")
	private String line;

}