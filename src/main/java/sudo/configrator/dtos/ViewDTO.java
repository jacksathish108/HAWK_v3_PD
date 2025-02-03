/*
 * 
 */
package sudo.configrator.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import sudo.configrator.entities.QuestionInfo;
import sudo.configrator.entities.ViewInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ViewDTO {

	/* COMMON FOR ALL START */
	private Long id;
	private Timestamp updateDate;
	private Timestamp createDate;
	private String createBy;
	private String updateBy;
	/* COMMON FOR ALL END */
	private Integer tabOrder;
	private String viewCode;
	private String name;
	private String description;
	private int status;
	private Map<String, QuestionDTO> applicableQtagMap = new HashMap<>();
	List<String> applicableQtagList;

	public ViewDTO(ViewInfo newViewInfo) {
		if (Objects.nonNull(newViewInfo)) {
			this.tabOrder = newViewInfo.getTabOrder();
			this.id = newViewInfo.getId();
			this.createDate = newViewInfo.getCreateDate();
			this.createBy = newViewInfo.getCreateBy();
			if (newViewInfo.getUpdateDate() == null)
				this.updateDate = newViewInfo.getCreateDate();
			else
				this.updateDate = newViewInfo.getUpdateDate();
			if (newViewInfo.getUpdateBy() == null)
				this.updateBy = newViewInfo.getCreateBy();
			else
				this.updateBy = newViewInfo.getUpdateBy();

			this.name = newViewInfo.getName();
			this.viewCode = newViewInfo.getViewCode();
			this.description = newViewInfo.getDescription();
			this.status = newViewInfo.getStatus();
			newViewInfo.getApplicableQtagMap().sort(Comparator.comparing(QuestionInfo::getIndex));
			
			newViewInfo.getApplicableQtagMap().forEach(question -> {
				this.applicableQtagMap.put(question.getQTag(), new QuestionDTO(question));
			});
		}
	}

	public ViewInfo viewDetailsDTO() {
		ViewInfo viewInfo = new ViewInfo();
		if (Objects.nonNull(id))
			viewInfo.setId(id);
		if (Objects.nonNull(tabOrder))
			viewInfo.setTabOrder(tabOrder);
		if (Objects.nonNull(createBy))
			viewInfo.setCreateBy(createBy);
		if (Objects.nonNull(createDate))
			viewInfo.setCreateDate(createDate);
		if (Objects.nonNull(description))
			viewInfo.setDescription(description);
		if (Objects.nonNull(name))
			viewInfo.setName(name);
		if (Objects.nonNull(updateDate))
			viewInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(updateBy))
			viewInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(description))
			viewInfo.setDescription(description);
		if (Objects.nonNull(status))
			viewInfo.setStatus(status);
		if (Objects.nonNull(viewCode))
			viewInfo.setViewCode(viewCode);
		if (Objects.nonNull(applicableQtagList)) {
			List<QuestionInfo> question = new ArrayList<>();
			if (applicableQtagList != null)
				applicableQtagList.forEach(k -> {
					question.add(new QuestionDTO(k).QuestionInfoDTO());
				});
			viewInfo.setApplicableQtagMap(question);
		}
		return viewInfo;
	}

	protected ViewDTO(Long id) {
		super();
		this.id = id;
	}
}
