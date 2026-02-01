package hu.ps.ss.data.entity;

import lombok.*;

import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EntityBase extends IdentifierBase {

	@Column(name="FIDO")
	private LocalDateTime modified;

	@Column(name="FUSER", length = 70)
	private String modifiedBy;
}
