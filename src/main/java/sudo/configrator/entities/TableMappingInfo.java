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
import sudo.utils.HawkResources;

// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "table_mapping_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class TableMappingInfo implements Serializable {

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
	@Column(name = "Source_ViewId")
	@NotNull(message = "SourceViewId is required")
	Long sourceViewId;
	@Column(name = "Target_Table")
	@NotNull(message = "Target_Table is required")
	Long targetTable;

	@Column(name = "Name")
	@NotNull(message = "Name is required")
	String name;
	@Column(name = "Description", nullable = true)
	String description;
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;
	@Column(name = "Sql_Query", nullable = true)
	String sqlQuery;
	
	@Column(name = "Column_Map")
	String columnMap;
	// protected Map<String, String> columnMap ; // maps from attribute name to value

	public List update(TableMappingInfo ptablemap) {
		List<Object> changeHistoryList = new ArrayList<>();

		if (!Objects.equals(this.updateDate, ptablemap.getUpdateDate())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateDate", updateDate, ptablemap.getUpdateDate()));
			this.updateDate = ptablemap.getUpdateDate();
		}
		if (!Objects.equals(this.updateBy, ptablemap.getUpdateBy())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateBy", updateBy, ptablemap.getUpdateBy()));
			this.updateBy = ptablemap.getUpdateBy();
		}

		if (!Objects.equals(this.name, ptablemap.getName())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("name", name, ptablemap.getName()));
			this.name = ptablemap.getName();
		}

		if (!Objects.equals(this.description, ptablemap.getDescription())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("description", description, ptablemap.getDescription()));
			this.description = ptablemap.getDescription();
		}

		if (!Objects.equals(this.status, ptablemap.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("status", status, ptablemap.getStatus()));
			this.status = ptablemap.getStatus();
		}

		if (!Objects.equals(this.sourceWebPageId, ptablemap.getSourceWebPageId())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("sourceWebPageId", sourceWebPageId,
					ptablemap.getSourceWebPageId()));
			this.sourceWebPageId = ptablemap.getSourceWebPageId();
		}

		if (!Objects.equals(this.targetTable, ptablemap.getTargetTable())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("targetTable", targetTable, ptablemap.getTargetTable()));
			this.targetTable = ptablemap.getTargetTable();
		}

		if (!Objects.equals(this.sourceViewId, ptablemap.getSourceViewId())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("SourceViewId", sourceViewId, ptablemap.getSourceViewId()));
			this.sourceViewId = ptablemap.getSourceViewId();
		}

		if (!Objects.equals(this.dataMapCode, ptablemap.getDataMapCode())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("viewCode", dataMapCode, ptablemap.getDataMapCode()));
			this.dataMapCode = ptablemap.getDataMapCode();
		}
		if (!Objects.equals(this.sqlQuery, ptablemap.getSqlQuery())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("sqlQuery", sqlQuery, ptablemap.getSqlQuery()));
			this.sqlQuery = ptablemap.getSqlQuery();
		}

		
		if (!Objects.equals(this.columnMap, ptablemap.getColumnMap())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("columnMap", columnMap, ptablemap.getColumnMap()));
			this.columnMap = ptablemap.getColumnMap();
		}

		return changeHistoryList;
	}
}
