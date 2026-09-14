package hu.ps.ss.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class IdentifierBase {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="FAZONOSITO")
	private int id;

}
