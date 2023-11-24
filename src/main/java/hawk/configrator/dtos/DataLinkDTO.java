/*
 * 
 */
package hawk.configrator.dtos;

import java.sql.Timestamp;
import java.util.Objects;

import hawk.configrator.entities.DataLinkInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataLinkDTO {

	/* COMMON FOR ALL START */
	private Long id;
	private Timestamp updateDate;
	private Timestamp createDate;
	private String createBy;
	private String updateBy;
	/* COMMON FOR ALL END */
	private String linkCode;
	private String name;
	private String description;
	private int status;
	private String sourceWebPageCode;
	private String targetWebPageCode;
	private Integer sourceViewId;
	private Integer targetViewId;

	//private Map<String, String>  qtagMap;
	private String qtagMap;

	public DataLinkDTO(DataLinkInfo dataLinkInfo) {
		if (Objects.nonNull(dataLinkInfo)) {
			this.linkCode = dataLinkInfo.getLinkCode();
			this.id = dataLinkInfo.getId();
			this.createDate = dataLinkInfo.getCreateDate();
			this.createBy = dataLinkInfo.getCreateBy();
			if (dataLinkInfo.getUpdateDate() == null)
				this.updateDate = dataLinkInfo.getCreateDate();
			else
				this.updateDate = dataLinkInfo.getUpdateDate();
			if (dataLinkInfo.getUpdateBy() == null)
				this.updateBy = dataLinkInfo.getCreateBy();
			else
				this.updateBy = dataLinkInfo.getUpdateBy();

			this.name = dataLinkInfo.getName();
			this.description = dataLinkInfo.getDescription();
			this.status = dataLinkInfo.getStatus();

			this.sourceWebPageCode = dataLinkInfo.getSourceWebPageCode();
			this.targetWebPageCode = dataLinkInfo.getTargetWebPageCode();
			this.sourceViewId = dataLinkInfo.getSourceViewId();
			this.targetViewId = dataLinkInfo.getTargetViewId();
			this.qtagMap = dataLinkInfo.getQtagMap();
		}
	}

	public DataLinkInfo dataLinkDetailsDTO() {
		DataLinkInfo dataLinkInfo = new DataLinkInfo();
		if (Objects.nonNull(id))
			dataLinkInfo.setId(id);
		if (Objects.nonNull(linkCode))
			dataLinkInfo.setLinkCode(linkCode);
		if (Objects.nonNull(createBy))
			dataLinkInfo.setCreateBy(createBy);
		if (Objects.nonNull(createDate))
			dataLinkInfo.setCreateDate(createDate);
		if (Objects.nonNull(description))
			dataLinkInfo.setDescription(description);
		if (Objects.nonNull(name))
			dataLinkInfo.setName(name);
		if (Objects.nonNull(updateDate))
			dataLinkInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(updateBy))
			dataLinkInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(description))
			dataLinkInfo.setDescription(description);
		if (Objects.nonNull(status))
			dataLinkInfo.setStatus(status);

		
		if (Objects.nonNull(sourceWebPageCode))
			dataLinkInfo.setSourceWebPageCode(sourceWebPageCode);
		if (Objects.nonNull(targetWebPageCode))
			dataLinkInfo.setTargetWebPageCode(targetWebPageCode);
		if (Objects.nonNull(sourceViewId))
			dataLinkInfo.setSourceViewId(sourceViewId);
		if (Objects.nonNull(targetViewId))
			dataLinkInfo.setTargetViewId(targetViewId);
		if (Objects.nonNull(qtagMap))
			dataLinkInfo.setQtagMap(qtagMap);
		
		
		
		return dataLinkInfo;
	}

	protected DataLinkDTO(Long id) {
		super();
		this.id = id;
	}
}
