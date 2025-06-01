/*
 * 
 */
package sudo.configrator.dtos;

import java.sql.Timestamp;
import java.util.Objects;

import sudo.configrator.entities.DataMapingInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataMapingDTO {

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
	private Long targetWebPageId;
	private Long sourceViewId;
	private Long targetViewId;

	//private Map<String, String>  qtagMap;
	private String qtagMap;

	public DataMapingDTO(DataMapingInfo DataMapingInfo) {
		if (Objects.nonNull(DataMapingInfo)) {
			this.dataMapCode = DataMapingInfo.getDataMapCode();
			this.id = DataMapingInfo.getId();
			this.createDate = DataMapingInfo.getCreateDate();
			this.createBy = DataMapingInfo.getCreateBy();
			if (DataMapingInfo.getUpdateDate() == null)
				this.updateDate = DataMapingInfo.getCreateDate();
			else
				this.updateDate = DataMapingInfo.getUpdateDate();
			if (DataMapingInfo.getUpdateBy() == null)
				this.updateBy = DataMapingInfo.getCreateBy();
			else
				this.updateBy = DataMapingInfo.getUpdateBy();

			this.name = DataMapingInfo.getName();
			this.description = DataMapingInfo.getDescription();
			this.status = DataMapingInfo.getStatus();

			this.sourceWebPageId = DataMapingInfo.getSourceWebPageId();
			this.targetWebPageId = DataMapingInfo.getTargetWebPageId();
			this.sourceViewId = DataMapingInfo.getSourceViewId();
			this.targetViewId = DataMapingInfo.getTargetViewId();
			this.qtagMap = DataMapingInfo.getQtagMap();
		}
	}

	public DataMapingInfo DataMapingDetailsDTO() {
		DataMapingInfo DataMapingInfo = new DataMapingInfo();
		if (Objects.nonNull(id))
			DataMapingInfo.setId(id);
		if (Objects.nonNull(dataMapCode))
			DataMapingInfo.setDataMapCode(dataMapCode);
		if (Objects.nonNull(createBy))
			DataMapingInfo.setCreateBy(createBy);
		if (Objects.nonNull(createDate))
			DataMapingInfo.setCreateDate(createDate);
		if (Objects.nonNull(description))
			DataMapingInfo.setDescription(description);
		if (Objects.nonNull(name))
			DataMapingInfo.setName(name);
		if (Objects.nonNull(updateDate))
			DataMapingInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(updateBy))
			DataMapingInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(description))
			DataMapingInfo.setDescription(description);
		if (Objects.nonNull(status))
			DataMapingInfo.setStatus(status);

		
		if (Objects.nonNull(sourceWebPageId))
			DataMapingInfo.setSourceWebPageId(sourceWebPageId);
		if (Objects.nonNull(targetWebPageId))
			DataMapingInfo.setTargetWebPageId(targetWebPageId);
		if (Objects.nonNull(sourceViewId))
			DataMapingInfo.setSourceViewId(sourceViewId);
		if (Objects.nonNull(targetViewId))
			DataMapingInfo.setTargetViewId(targetViewId);
		if (Objects.nonNull(qtagMap))
			DataMapingInfo.setQtagMap(qtagMap);
		
		
		
		return DataMapingInfo;
	}

	protected DataMapingDTO(Long id) {
		super();
		this.id = id;
	}
}
