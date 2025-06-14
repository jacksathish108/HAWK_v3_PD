/*
 * 
 */
package hawk.entities;

import java.sql.Timestamp;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "client_id_generator")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class ClientIdGenerator {

	/* COMMON FOR ALL START */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "Create_Date")
	Timestamp createDate;
	@Column(name = "Create_By")
	String createBy;
	/* COMMON FOR ALL END */
	@Column(name = "Client_Id", unique = true)
	String clientId;
	String WebPageCode;
	/**
	 * @param createDate
	 * @param createBy
	 * @param WebPageCode
	 */
	public ClientIdGenerator(Timestamp createDate, String createBy, String WebPageCode) {
		super();
		this.createDate = createDate;
		this.createBy = createBy;
		this.WebPageCode = WebPageCode;
	}
}
