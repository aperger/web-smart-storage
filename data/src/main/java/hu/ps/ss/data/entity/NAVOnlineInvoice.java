package hu.ps.ss.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The persistent class for the TNAVONLINESZAMLA database table.
 * 
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="TNAVONLINESZAMLA")
@NamedQuery(name = "NAVOnlineInvoice.findAll", query = "SELECT noi FROM NAVOnlineInvoice noi ORDER BY noi.invoiceId DESC")
public class NAVOnlineInvoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FSZAMLA")
	private int invoiceId;

	@Column(name="FERVENYESSEG")
	private LocalDateTime expiredAt;

	@Column(name="FIDO")
	private LocalDateTime modified;

	@Column(name="FNAVTRANACTIONID")
	private String navTransactionId;

	@Column(name="FPROGPELDANY")
	private String programInstance;

	@Column(name="FSTATUSZ")
	private String status;

	@Column(name="FUSER")
	private String user;

	@Column(name="FUZENET")
	private String message;

	//@Lob
	@Column(name="FXMLSZAMLA")
	//@Type(type = "org.hibernate.type.BinaryType")
	@JsonIgnore
	private byte[] xmlInvoice;

	//@Lob
	@Column(name="FXMLVALASZ")
	//@Type(type = "org.hibernate.type.BinaryType")
	@JsonIgnore
	private byte[] xmlResponse;

}