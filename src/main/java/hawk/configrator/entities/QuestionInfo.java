/*
 * 
 */
package hawk.configrator.entities;

import java.io.Serializable;
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
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "question_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class QuestionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6096637684458683590L;
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
	@Column(name = "Required")
	int required;
	@Column(name = "Style")
	String style;
	@Column(name = "Css_Class")
	String cssClass;
	@Column(name = "On_Click")
	String onClick;
	@Column(name = "Attributes")
	String attributes;
	@Column(name = "On_Change")
	String onChange;
	@Column(name = "Options")
	String options;
	@Column(name = "Unique_Ans")
	Integer unique;
	@Size(min = 0, max = 65555)
	@Column(name = "Jscript",columnDefinition="LONGTEXT")
	String jscript;
	
	
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

//		if (!Objects.equals(this.trigerAction, questionInfo.getTrigerAction())) {
//			changeHistoryList.add(
//					HawkResources.buildUpdateHistory("trigerAction", trigerAction, questionInfo.getTrigerAction()));
//			this.trigerAction = questionInfo.getTrigerAction();
//		}

		if (!Objects.equals(this.style, questionInfo.getStyle())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("style", style, questionInfo.getStyle()));
			this.style = questionInfo.getStyle();
		}
		if (!Objects.equals(this.required, questionInfo.getRequired())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("required", required, questionInfo.getRequired()));
			this.required = questionInfo.getRequired();
		}
		if (!Objects.equals(this.options, questionInfo.getOptions())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("options", options, questionInfo.getOptions()));
			this.options = questionInfo.getOptions();
		}
		if (!Objects.equals(this.cssClass, questionInfo.getCssClass())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("cssClass", cssClass, questionInfo.getCssClass()));
			this.cssClass = questionInfo.getCssClass();
		}

		if (!Objects.equals(this.onClick, questionInfo.getOnClick())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("onClick", onClick, questionInfo.getOnClick()));
			this.onClick = questionInfo.getOnClick();
		}
		if (!Objects.equals(this.onChange, questionInfo.getOnChange())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("onCHange", onChange, questionInfo.getOnChange()));
			this.onChange = questionInfo.getOnChange();
		}
		if (!Objects.equals(this.attributes, questionInfo.getAttributes())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("attributes", attributes, questionInfo.getAttributes()));
			this.attributes = questionInfo.getAttributes();
		}
		if (!Objects.equals(this.unique, questionInfo.getUnique())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("unique", unique, questionInfo.getUnique()));
			this.unique = questionInfo.getUnique();
		}
		if (!Objects.equals(this.jscript, questionInfo.getJscript())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("jscript", jscript, questionInfo.getJscript()));
			this.jscript = questionInfo.getJscript();
		}

		return changeHistoryList;
	}

}
