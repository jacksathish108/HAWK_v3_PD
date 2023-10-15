package hawk.configrator.dtos;

import java.sql.Timestamp;
import java.util.Objects;

import hawk.configrator.entities.QuestionInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionDTO {
	/* COMMON FOR ALL START */
	private Long id;
	Timestamp updateDate;
	Timestamp createDate;
	String createBy;
	String updateBy;
	/* COMMON FOR ALL END */
	String qTag;
	Integer index;
	String elementType;
	String dataType;
	String defaultValue;
	String name;
	String description;
	int status;
	String answerType;
	String trigerAction;
	String style;

	public QuestionDTO(QuestionInfo questionInfo) {
		if (questionInfo != null) {
			this.id = questionInfo.getId();
			this.createDate = questionInfo.getCreateDate();
			this.createBy = questionInfo.getCreateBy();
			if (questionInfo.getUpdateDate() == null)
				this.updateDate = questionInfo.getCreateDate();
			else
				this.updateDate = questionInfo.getUpdateDate();
			if (questionInfo.getUpdateBy() == null)
				this.updateBy = questionInfo.getCreateBy();
			else
				this.updateBy = questionInfo.getUpdateBy();

			this.qTag = questionInfo.getQTag();
			this.index = questionInfo.getIndex();
			this.elementType = questionInfo.getElementType();
			this.dataType = questionInfo.getDataType();
			this.defaultValue = questionInfo.getDefaultValue();
			this.name = questionInfo.getName();
			this.description = questionInfo.getDescription();
			this.status = questionInfo.getStatus();
			this.answerType = questionInfo.getAnswerType();
			this.trigerAction = questionInfo.getTrigerAction();
			this.style = questionInfo.getStyle();
		}
	}

	public QuestionInfo QuestionInfoDTO() {
		QuestionInfo questionInfo = new QuestionInfo();
		if (Objects.nonNull(id))
			questionInfo.setId(id);
		if (Objects.nonNull(updateDate))
			questionInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(createDate))
			questionInfo.setCreateDate(createDate);
		if (Objects.nonNull(createBy))
			questionInfo.setCreateBy(createBy);
		if (Objects.nonNull(updateBy))
			questionInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(qTag))
			questionInfo.setQTag(qTag);
		if (Objects.nonNull(index))
			questionInfo.setIndex(index);
		if (Objects.nonNull(elementType))
			questionInfo.setElementType(elementType);
		if (Objects.nonNull(dataType))
			questionInfo.setDataType(dataType);
		if (Objects.nonNull(defaultValue))
			questionInfo.setDefaultValue(defaultValue);
		if (Objects.nonNull(name))
			questionInfo.setName(name);
		if (Objects.nonNull(description))
			questionInfo.setDescription(description);
		if (Objects.nonNull(status))
			questionInfo.setStatus(status);
		if (Objects.nonNull(answerType))
			questionInfo.setAnswerType(answerType);
		if (Objects.nonNull(trigerAction))
			questionInfo.setTrigerAction(trigerAction);
		if (Objects.nonNull(style))
			questionInfo.setStyle(style);

		return questionInfo;
	}

	/**
	 * 
	 */
}
