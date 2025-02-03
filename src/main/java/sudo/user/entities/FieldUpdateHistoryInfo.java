/*
 * 
 */
package sudo.user.entities;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
/**
 * @author Sathishkumar.K
 *
 */
@Entity
@Table(name = "field_update_history_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class FieldUpdateHistoryInfo {

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
	@Column(name = "Module_Name")
	@NotNull(message = "Module Name is required")
	String moduleName;
	@Column(name = "Record_Id")
	@NotNull(message = "Record Id is required")
	Long recordId;

	@ElementCollection
	 @CollectionTable(name = "modifiedvalues")
	List<String> modifiedValues;
	public FieldUpdateHistoryInfo(long recordId,Timestamp createDate, String createBy,
			@NotNull(message = "Module Name is required") String moduleName,
			@NotNull(message = "Record Id is required") List<String> modifiedValues) {
		this.createDate = createDate;
		this.createBy = createBy;
		this.moduleName = moduleName;
		this.recordId = recordId;
		this.modifiedValues = modifiedValues;
	}
	public FieldUpdateHistoryInfo() {

	}

	

}
