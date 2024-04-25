/*
 * 
 */
package hawk.product.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import hawk.configrator.dtos.ListViewAnswerDTO;
import hawk.product.dtos.AnswersDTO;
import hawk.utils.HawkResources;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "answer_info")
@NamedNativeQuery(name = "getUniqueQuestionAnswers", query = "SELECT answerInfo_Id, qTag, ansValue\r\n"
		+ "FROM answers\r\n"
		+ "WHERE qTag IN (:qTags) AND ansValue IN (:ansValues)", resultSetMapping = "AnswersDTO")
@SqlResultSetMapping(name = "AnswersDTO", classes = @ConstructorResult(targetClass = AnswersDTO.class, columns = {
		@ColumnResult(name = "answerInfo_Id", type = Long.class), @ColumnResult(name = "qTag", type = String.class),
		@ColumnResult(name = "ansValue", type = String.class) }))

@NamedNativeQuery(name = "getAnswersByQtag", query = "SELECT ans.answerInfo_Id, ans.qTag, ans.ansValue\r\n"
		+ "FROM answers ans\r\n" + "INNER JOIN answer_info ansinf ON ansinf.id = ans.AnswerInfo_Id\r\n"
		+ "WHERE ansinf.status = 0 AND ans.qTag IN (:qTags);", resultSetMapping = "AnswersDTO")
@SqlResultSetMapping(name = "AnswersDTO1", classes = @ConstructorResult(targetClass = AnswersDTO.class, columns = {
		@ColumnResult(name = "answerInfo_Id", type = Long.class), @ColumnResult(name = "qTag", type = String.class),
		@ColumnResult(name = "ansValue", type = String.class) }))

@NamedNativeQuery(name = "getAnswersByListViewTargetQtags", query = "SELECT ans.answerInfo_Id,ans.qTag,ans.ansValue  FROM answers ans \r\n"
		+ "INNER JOIN answer_info ansinf ON ansinf.id=ans.AnswerInfo_Id WHERE ansinf.status=0 \r\n"
		+ "AND answerInfo_Id IN\r\n" + "(\r\n" + "SELECT answerInfo_Id FROM answers WHERE \r\n"
		+ "qTag IN(SELECT Target_Qtag  FROM listview_info  WHERE   Source_Qtag IN(:targetQtags) AND STATUS=1)\r\n"
		+ ")", resultSetMapping = "ListViewAnswerDTO")
@SqlResultSetMapping(name = "ListViewAnswerDTO", classes = @ConstructorResult(targetClass = ListViewAnswerDTO.class, columns = {
		@ColumnResult(name = "answerInfo_Id", type = Long.class), @ColumnResult(name = "qTag", type = String.class),
		@ColumnResult(name = "ansValue", type = String.class) }))

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class AnswerInfo implements Serializable {

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
	@Column(name = "View_Id")
	@NotNull(message = "QTag is required")
	Long viewId;
	@Column(name = "Page_Id")
	@NotNull(message = "Page_Id is required")
	Long pageId;
	@Column(name = "Discription")
	String discription;
	
	
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;
	@ElementCollection(targetClass = Answer.class)
	 @CollectionTable(name = "answers")
	List<Answer> answers;

	public List update(AnswerInfo answerInfo) {
		List<Object> changeHistoryList = new ArrayList<>();

		if (!Objects.equals(this.updateDate, answerInfo.getUpdateDate())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("updateDate", updateDate, answerInfo.getUpdateDate()));
			this.updateDate = answerInfo.getUpdateDate();
		}

		if (!Objects.equals(this.updateBy, answerInfo.getUpdateBy())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateBy", updateBy, answerInfo.getUpdateBy()));
			this.updateBy = answerInfo.getUpdateBy();
		}

		if (!Objects.equals(this.createDate, answerInfo.getCreateDate())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("createDate", createDate, answerInfo.getCreateDate()));
			this.createDate = answerInfo.getCreateDate();
		}

		if (!Objects.equals(this.createBy, answerInfo.getCreateBy())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("createBy", createBy, answerInfo.getCreateBy()));
			this.createBy = answerInfo.getCreateBy();
		}
		
		
		if (!Objects.equals(this.status, answerInfo.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("Status", status, answerInfo.getStatus()));
			this.status = answerInfo.getStatus();
		}
		
		
		if (!Objects.equals(this.discription, answerInfo.getDiscription())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("discription", discription, answerInfo.getDiscription()));
			this.discription = answerInfo.getDiscription();
		}

		if (!Objects.equals(this.viewId, answerInfo.getViewId())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("viewId", viewId, answerInfo.getViewId()));
			this.viewId = answerInfo.getViewId();
		}
		if (!Objects.equals(this.answers, answerInfo.getAnswers())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("answers", answers, answerInfo.getAnswers()));
			this.answers = answerInfo.getAnswers();
		}
		return changeHistoryList;
	}

}
