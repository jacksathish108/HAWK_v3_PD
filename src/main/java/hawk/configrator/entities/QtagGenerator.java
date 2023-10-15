/*
 * 
 */
package hawk.configrator.entities;

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
public class QtagGenerator {

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
	@Column(name = "Qtag", unique = true)
	String qtag;
	String prefixCode;
	/**
	 * @param createDate
	 * @param createBy
	 * @param WebPageCode
	 */
	public QtagGenerator(Timestamp createDate, String createBy, String prefixCode) {
		super();
		this.createDate = createDate;
		this.createBy = createBy;
		this.prefixCode = prefixCode;
	}
}
