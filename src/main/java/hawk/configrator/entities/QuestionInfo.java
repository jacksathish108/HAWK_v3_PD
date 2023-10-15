/*
 * 
 */
package hawk.configrator.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import hawk.utils.HawkResources;
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

// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "Question_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class QuestionInfo {

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
	@Column(name = "QTag", unique = true)
	@NotNull(message = "QTag is required")
	String qTag;
	@Column(name = "Q_Index")
	@NotNull(message = "Index is required")
	Integer index;
	@Column(name = "Element_Type")
	@NotNull(message = "Element Type is required")
	String elementType;
	@Column(name = "Data_Type")
	@NotNull(message = "Data_Type is required")
	String dataType;
	@Column(name = "Default_Value")
	@NotNull(message = "Default is required")
	String defaultValue;
	@Column(name = "Name")
	@NotNull(message = "Name is required")
	String name;
	@Column(name = "Description")
	@NotNull(message = "Description is required")
	String description;
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;
	@Column(name = "Answer_Type")
	@NotNull(message = "Answer_Type is required")
	String answerType;
	@Column(name = "Triger_Action")
	String trigerAction;
	@Column(name = "Style")
	String style;

	public List update(QuestionInfo questionInfo) {
		List<Object> changeHistoryList = new ArrayList<>();

		if (!Objects.equals(this.updateDate, questionInfo.getUpdateDate())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("updateDate", updateDate, questionInfo.getUpdateDate()));
			this.updateDate = questionInfo.getUpdateDate();
		}

		if (!Objects.equals(this.updateBy, questionInfo.getUpdateBy())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateBy", updateBy, questionInfo.getUpdateBy()));
			this.updateBy = questionInfo.getUpdateBy();
		}
		if (!Objects.equals(this.index, questionInfo.getIndex())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("index", index, questionInfo.getIndex()));
			this.index = questionInfo.getIndex();
		}

		if (!Objects.equals(this.elementType, questionInfo.getElementType())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("elementType", elementType, questionInfo.getElementType()));
			this.elementType = questionInfo.getElementType();
		}

		if (!Objects.equals(this.dataType, questionInfo.getDataType())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("dataType", dataType, questionInfo.getDataType()));
			this.dataType = questionInfo.getDataType();
		}

		if (!Objects.equals(this.defaultValue, questionInfo.getDefaultValue())) {
			changeHistoryList.add(
					HawkResources.buildUpdateHistory("defaultValue", defaultValue, questionInfo.getDefaultValue()));
			this.defaultValue = questionInfo.getDefaultValue();
		}

		if (!Objects.equals(this.name, questionInfo.getName())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("name", name, questionInfo.getName()));
			this.name = questionInfo.getName();
		}

		if (!Objects.equals(this.description, questionInfo.getDescription())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("description", description, questionInfo.getDescription()));
			this.description = questionInfo.getDescription();
		}

		if (!Objects.equals(this.status, questionInfo.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("status", status, questionInfo.getStatus()));
			this.status = questionInfo.getStatus();
		}

		if (!Objects.equals(this.answerType, questionInfo.getAnswerType())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("answerType", answerType, questionInfo.getAnswerType()));
			this.answerType = questionInfo.getAnswerType();
		}

		if (!Objects.equals(this.trigerAction, questionInfo.getTrigerAction())) {
			changeHistoryList.add(
					HawkResources.buildUpdateHistory("trigerAction", trigerAction, questionInfo.getTrigerAction()));
			this.trigerAction = questionInfo.getTrigerAction();
		}

		if (!Objects.equals(this.style, questionInfo.getStyle())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("style", style, questionInfo.getStyle()));
			this.style = questionInfo.getStyle();
		}

		return changeHistoryList;
	}

}
