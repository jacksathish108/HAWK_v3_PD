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
import jakarta.persistence.Column;
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
@Entity
@Table(name = "datamaping_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class DataMapingInfo implements Serializable {

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
	@Column(name = "DataMap_Code", unique = true)
	@NotNull(message = "dataMap_Code is required")
	String dataMapCode;
	@Column(name = "Source_WebPageId")
	@NotNull(message = "SourceWebPage_Id is required")
	Long sourceWebPageId;
	@Column(name = "Target_WebPageId")
	@NotNull(message = "Target_WebPageCode is required")
	Long targetWebPageId;
	@Column(name = "Source_ViewId")
	@NotNull(message = "SourceViewId is required")
	Long sourceViewId;
	@Column(name = "Target_ViewId")
	@NotNull(message = "TargetViewId is required")
	Long targetViewId;

	@Column(name = "Name")
	@NotNull(message = "Name is required")
	String name;
	@Column(name = "Description", nullable = true)
	String description;
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;
	@Column(name = "Qtag_Map")
	String qtagMap;
	// protected Map<String, String> qtagMap ; // maps from attribute name to value

	public List update(DataMapingInfo viewInfo) {
		List<Object> changeHistoryList = new ArrayList<>();

		if (!Objects.equals(this.updateDate, viewInfo.getUpdateDate())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateDate", updateDate, viewInfo.getUpdateDate()));
			this.updateDate = viewInfo.getUpdateDate();
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

		if (!Objects.equals(this.sourceWebPageId, viewInfo.getSourceWebPageId())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("sourceWebPageId", sourceWebPageId,
					viewInfo.getSourceWebPageId()));
			this.sourceWebPageId = viewInfo.getSourceWebPageId();
		}

		if (!Objects.equals(this.targetWebPageId, viewInfo.getTargetWebPageId())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("targetWebPageId", targetWebPageId,
					viewInfo.getTargetWebPageId()));
			this.targetWebPageId = viewInfo.getTargetWebPageId();
		}

		if (!Objects.equals(this.sourceViewId, viewInfo.getSourceViewId())) {
			changeHistoryList.add(
					HawkResources.buildUpdateHistory("SourceViewId", sourceViewId, viewInfo.getSourceViewId()));
			this.sourceViewId = viewInfo.getSourceViewId();
		}

		if (!Objects.equals(this.targetViewId, viewInfo.getTargetViewId())) {
			changeHistoryList.add(
					HawkResources.buildUpdateHistory("targetViewId", targetViewId, viewInfo.getTargetViewId()));
			this.targetViewId = viewInfo.getTargetViewId();
		}

		if (!Objects.equals(this.dataMapCode, viewInfo.getDataMapCode())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("viewCode", dataMapCode, viewInfo.getDataMapCode()));
			this.dataMapCode = viewInfo.getDataMapCode();
		}

		if (!Objects.equals(this.qtagMap, viewInfo.getQtagMap())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("qtagMap", qtagMap, viewInfo.getQtagMap()));
			this.qtagMap = viewInfo.getQtagMap();
		}

		return changeHistoryList;
	}
}
