/*
 * 
 */
package sudo.configrator.dtos;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import sudo.configrator.entities.TableMappingInfo;

@Setter
@Getter
public class TableMappingDTO {

	/* COMMON FOR ALL START */
	private Long id;
	private Timestamp updateDate;
	private Timestamp createDate;
	private String createBy;
	private String updateBy;
	/* COMMON FOR ALL END */
	private String dataMapCode;
	private String name;
	private String description;
	private int status;
	private Long sourceWebPageId;
	private Long targetTable;
	private Long sourceViewId;
	private String sqlQuery;

	// private Map<String, String> columnMap;
	private String columnMap;

	public TableMappingDTO(TableMappingInfo TableMappingInfo) {
		if (Objects.nonNull(TableMappingInfo)) {
			this.dataMapCode = TableMappingInfo.getDataMapCode();
			this.id = TableMappingInfo.getId();
			this.createDate = TableMappingInfo.getCreateDate();
			this.createBy = TableMappingInfo.getCreateBy();
			if (TableMappingInfo.getUpdateDate() == null)
				this.updateDate = TableMappingInfo.getCreateDate();
			else
				this.updateDate = TableMappingInfo.getUpdateDate();
			if (TableMappingInfo.getUpdateBy() == null)
				this.updateBy = TableMappingInfo.getCreateBy();
			else
				this.updateBy = TableMappingInfo.getUpdateBy();

			this.name = TableMappingInfo.getName();
			this.description = TableMappingInfo.getDescription();
			this.status = TableMappingInfo.getStatus();
			this.sqlQuery = TableMappingInfo.getSqlQuery();

			this.sourceWebPageId = TableMappingInfo.getSourceWebPageId();
			this.targetTable = TableMappingInfo.getTargetTable();
			this.sourceViewId = TableMappingInfo.getSourceViewId();
			this.columnMap = TableMappingInfo.getColumnMap();
		}
	}

	public TableMappingInfo TableMappingDetailsDTO() {
		TableMappingInfo TableMappingInfo = new TableMappingInfo();
		if (Objects.nonNull(id))
			TableMappingInfo.setId(id);
		if (Objects.nonNull(dataMapCode))
			TableMappingInfo.setDataMapCode(dataMapCode);
		if (Objects.nonNull(createBy))
			TableMappingInfo.setCreateBy(createBy);
		if (Objects.nonNull(createDate))
			TableMappingInfo.setCreateDate(createDate);
		if (Objects.nonNull(description))
			TableMappingInfo.setDescription(description);
		if (Objects.nonNull(name))
			TableMappingInfo.setName(name);
		if (Objects.nonNull(updateDate))
			TableMappingInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(updateBy))
			TableMappingInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(description))
			TableMappingInfo.setDescription(description);
		if (Objects.nonNull(status))
			TableMappingInfo.setStatus(status);

		if (Objects.nonNull(sourceWebPageId))
			TableMappingInfo.setSourceWebPageId(sourceWebPageId);
		if (Objects.nonNull(targetTable))
			TableMappingInfo.setTargetTable(targetTable);
		if (Objects.nonNull(sourceViewId))
			TableMappingInfo.setSourceViewId(sourceViewId);
		if (Objects.nonNull(columnMap))
			TableMappingInfo.setColumnMap(columnMap);
		if (Objects.nonNull(sqlQuery))
			TableMappingInfo.setSqlQuery(sqlQuery);

		return TableMappingInfo;
	}

	protected TableMappingDTO(Long id) {
		super();
		this.id = id;
	}
}
