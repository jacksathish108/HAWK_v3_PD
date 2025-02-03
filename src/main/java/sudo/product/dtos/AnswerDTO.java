package sudo.product.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import sudo.product.entities.Answer;
import sudo.product.entities.AnswerInfo;
import sudo.utils.CommonUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerDTO {
	protected AnswerDTO(String qTag) {

	}

	/* COMMON FOR ALL START */
	private Long id;
	Timestamp updateDate;
	Timestamp createDate;
	String createBy;
	String updateBy;
	/* COMMON FOR ALL END */
	Long viewId;
	Long pageId;;
	List<Answer> answers;
	int status;
	String discription;

	public AnswerDTO(AnswerInfo answerInfo) {
		if (answerInfo != null) {
			this.id = answerInfo.getId();
			this.createDate = answerInfo.getCreateDate();
			this.createBy = answerInfo.getCreateBy();
			if (answerInfo.getUpdateDate() == null)
				this.updateDate = answerInfo.getCreateDate();
			else
				this.updateDate = answerInfo.getUpdateDate();
			if (answerInfo.getUpdateBy() == null)
				this.updateBy = answerInfo.getCreateBy();
			else
				this.updateBy = answerInfo.getUpdateBy();

			
			this.discription = answerInfo.getDiscription();
			this.status = answerInfo.getStatus();
			this.pageId = answerInfo.getPageId();
			this.viewId = answerInfo.getViewId();
			this.answers = answerInfo.getAnswers();

		}
	}

	public AnswerDTO() {
		// TODO Auto-generated constructor stub
	}

	public AnswerDTO(Map answers) {
		try {
			if (answers != null && answers.get("viewId") != null && answers.get("pageId") != null
					&& answers.get("answers") != null) {
				Long viewId = null;
				Long ansId = null;
				Long pageId = null;
				String discription="";
				if (CommonUtil.isStringNumeric((String) answers.get("viewId")))
					viewId = Long.valueOf((String) answers.get("viewId"));


				if (CommonUtil.isStringNumeric((String) answers.get("pageId")))
					pageId = Long.valueOf((String) answers.get("pageId"));
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> map = mapper.readValue(String.valueOf(answers.get("answers")), Map.class);
				if (CommonUtil.isStringNumeric((String) map.get("id")))
					ansId = Long.valueOf((String) map.get("id"));
				this.answers = new ArrayList<>();
				this.pageId = pageId;
				this.viewId = viewId;
				this.id = ansId;
				this.discription=discription;
				map.forEach((key, value) -> {
					{
						if(key.contains("Q"))
						{
						Answer ans = new Answer();
						ans.setAnsValue(value);
						ans.setQTag(key);
						ans.setType("Native");
						this.answers.add(ans);
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AnswerInfo AnswerInfoDTO() {
		AnswerInfo answerInfo = new AnswerInfo();
		if (Objects.nonNull(id))
			answerInfo.setId(id);
		if (Objects.nonNull(updateDate))
			answerInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(createDate))
			answerInfo.setCreateDate(createDate);
		if (Objects.nonNull(createBy))
			answerInfo.setCreateBy(createBy);
		if (Objects.nonNull(updateBy))
			answerInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(status))
			answerInfo.setStatus(status);
		if (Objects.nonNull(discription))
			answerInfo.setDiscription(discription);
		if (Objects.nonNull(pageId))
			answerInfo.setPageId(pageId);
		if (Objects.nonNull(viewId))
			answerInfo.setViewId(viewId);
		if (Objects.nonNull(answers))
			answerInfo.setAnswers(answers);

		return answerInfo;
	}

	/**
	 * 
	 */
}
