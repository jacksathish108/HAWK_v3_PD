/*
 * 
 */
package sudo.configrator.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import sudo.utils.HawkResources;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "view_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class ViewInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* COMMON FOR ALL START */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "Update_Date")
	Timestamp updateDate;
	@Column(name = "Create_Date")
	Timestamp createDate;
	@Column(name = "Create_By")
	String createBy;
	@Column(name = "Update_By")
	String updateBy;
	/* COMMON FOR ALL END */
	@Column(name = "View_Code", unique = true)
	@NotNull(message = "View_Code is required")
	String viewCode;
	@Column(name = "Tab_Order")
	@NotNull(message = "Tab Order is required")
	Integer tabOrder;

	@Column(name = "Name")
	@NotNull(message = "Name is required")
	String name;
	@Column(name = "Description", nullable = true)
	String description;
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;
	 @CollectionTable(name = "applicableqtagmap")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "QtagTagId", referencedColumnName = "id")
	@OrderBy(value = "index asc ")
	List<QuestionInfo> applicableQtagMap;

	public List update(ViewInfo viewInfo) {
		List<Object> changeHistoryList = new ArrayList<>();

		if (!Objects.equals(this.updateDate, viewInfo.getUpdateDate())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateDate", updateDate, viewInfo.getUpdateDate()));
			this.updateDate = viewInfo.getUpdateDate();
		}
		if (!Objects.equals(this.tabOrder, viewInfo.getTabOrder())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("tabOrder", tabOrder, viewInfo.getTabOrder()));
			this.tabOrder = viewInfo.getTabOrder();
		}
		if (!Objects.equals(this.updateBy, viewInfo.getUpdateBy())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateBy", updateBy, viewInfo.getUpdateBy()));
			this.updateBy = viewInfo.getUpdateBy();
		}

		if (!Objects.equals(this.name, viewInfo.getName())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("name", name, viewInfo.getName()));
			this.name = viewInfo.getName();
		}

		if (!Objects.equals(this.description, viewInfo.getDescription())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("description", description, viewInfo.getDescription()));
			this.description = viewInfo.getDescription();
		}

		if (!Objects.equals(this.status, viewInfo.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("status", status, viewInfo.getStatus()));
			this.status = viewInfo.getStatus();
		}
		if (!Objects.equals(this.status, viewInfo.getViewCode())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("viewCode", viewCode, viewInfo.getViewCode()));
			this.viewCode = viewInfo.getViewCode();
		}

		if (!Objects.equals(this.applicableQtagMap, viewInfo.getApplicableQtagMap())) {
			changeHistoryList.add(
					HawkResources.buildUpdateHistory("questions", applicableQtagMap, viewInfo.getApplicableQtagMap()));
			this.applicableQtagMap = viewInfo.getApplicableQtagMap();
		}

		return changeHistoryList;
	}
}
