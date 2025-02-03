/*
 * 
 */
package sudo.product.dtos;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sudo.product.entities.ListViewInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListViewDTO {

	/* COMMON FOR ALL START */
	private Long id;
	private Timestamp updateDate;
	private Timestamp createDate;
	private String createBy;
	private String updateBy;
	/* COMMON FOR ALL END */
	private String listViewCode;
	private String sourceQtag;
	private String description;
	private int status;
	private String targetQtag;
	private String dependencyCondition;

	public ListViewDTO(ListViewInfo listViewInfo) {
		if (Objects.nonNull(listViewInfo)) {
			this.listViewCode = listViewInfo.getListViewCode();
			this.id = listViewInfo.getId();
			this.createDate = listViewInfo.getCreateDate();
			this.createBy = listViewInfo.getCreateBy();
			if (listViewInfo.getUpdateDate() == null)
				this.updateDate = listViewInfo.getCreateDate();
			else
				this.updateDate = listViewInfo.getUpdateDate();
			if (listViewInfo.getUpdateBy() == null)
				this.updateBy = listViewInfo.getCreateBy();
			else
				this.updateBy = listViewInfo.getUpdateBy();

			this.description = listViewInfo.getDescription();
			this.status = listViewInfo.getStatus();

			this.sourceQtag = listViewInfo.getSourceQtag();
			this.targetQtag = listViewInfo.getTargetQtag();
			this.dependencyCondition = listViewInfo.getDependencyCondition();
		}
	}

	public ListViewInfo listViewDetailsDTO() {
		ListViewInfo listViewInfo = new ListViewInfo();
		if (Objects.nonNull(id))
			listViewInfo.setId(id);
		if (Objects.nonNull(listViewCode))
			listViewInfo.setListViewCode(listViewCode);
		if (Objects.nonNull(createBy))
			listViewInfo.setCreateBy(createBy);
		if (Objects.nonNull(createDate))
			listViewInfo.setCreateDate(createDate);
		if (Objects.nonNull(description))
			listViewInfo.setDescription(description);
		if (Objects.nonNull(updateDate))
			listViewInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(updateBy))
			listViewInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(description))
			listViewInfo.setDescription(description);
		if (Objects.nonNull(status))
			listViewInfo.setStatus(status);

		if (Objects.nonNull(sourceQtag))
			listViewInfo.setSourceQtag(sourceQtag);
		if (Objects.nonNull(targetQtag))
			listViewInfo.setTargetQtag(targetQtag);
		if (Objects.nonNull(dependencyCondition))
			listViewInfo.setDependencyCondition(dependencyCondition);

		return listViewInfo;
	}

	public ListViewDTO(Long id) {
		super();
		this.id = id;
	}

	public ListViewDTO(String listViewCode, String sourceQtag, String targetQtag, String dependencyCondition) {
		super();
		this.listViewCode = listViewCode;
		this.sourceQtag = sourceQtag;
		this.targetQtag = targetQtag;
		this.dependencyCondition = dependencyCondition;
	}
}
