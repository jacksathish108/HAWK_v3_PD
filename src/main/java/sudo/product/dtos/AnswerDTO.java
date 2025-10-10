package sudo.product.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import sudo.configrator.dtos.QuestionDTO;
import sudo.configrator.dtos.ViewDTO;
import sudo.product.entities.Answer;
import sudo.product.entities.AnswerInfo;
import sudo.utils.CommonUtil;
import sudo.utils.FileStorageService;

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
	int deleteStatus;
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
			this.deleteStatus = answerInfo.getDeleteStatus();
			this.pageId = answerInfo.getPageId();
			this.viewId = answerInfo.getViewId();
			this.answers = answerInfo.getAnswers();

		}
	}

	public AnswerDTO() {
		// TODO Auto-generated constructor stub
	}

	public AnswerDTO(Map<String, String> answersMap) {
		try {
			if (answersMap != null) {
				Long viewId = null;
				Long pageId = null;
				Long ansId = null;

				if (CommonUtil.isStringNumeric(answersMap.get("viewId")))
					viewId = Long.valueOf(answersMap.get("viewId"));
				if (CommonUtil.isStringNumeric(answersMap.get("pageId")))
					pageId = Long.valueOf(answersMap.get("pageId"));

				this.pageId = pageId;
				this.viewId = viewId;
				
				this.answers = new ArrayList<>();

				 ObjectMapper objectMapper = new ObjectMapper();

			        // ✅ Parse JSON string into Map
			        Map<String, String> map = objectMapper.readValue(answersMap.get("answers"), Map.class);

			        // ✅ Prepare answer listw

					if (CommonUtil.isStringNumeric(map.get("id")))
						ansId = Long.valueOf(map.get("id"));
					this.id = ansId;

			        for (Map.Entry<String, String> entry : map.entrySet()) {
			            String key = entry.getKey();
			            String value = entry.getValue();

			            if (key.startsWith("Q_")) {
			                Answer ans = new Answer();
			                ans.setQTag(key);
			                ans.setAnsValue(value);
			                ans.setType("Native");
			                answers.add(ans);
			            }
			        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AnswerDTO(Map<String, String> answersMap, Map<String, MultipartFile> filesMap,
			FileStorageService fileStorageService, Map<String, QuestionDTO> qtagMap) {
		this(answersMap); // call base constructor first

		if (filesMap != null) {

			for (Map.Entry<String, MultipartFile> entry : filesMap.entrySet()) {
				String key = entry.getKey();
				MultipartFile file = entry.getValue();
				if (!file.isEmpty() && key.startsWith("Q_")) {

					String dirValue = CommonUtil.extractAttributes(qtagMap.get(key).getAttributes());

					String fileUrl = fileStorageService.save(file, dirValue.trim());
					Answer fileAnswer = new Answer();
					fileAnswer.setQTag(key);
					fileAnswer.setAnsValue(fileUrl); // Save file path or URL
					fileAnswer.setType("File");
					this.answers.add(fileAnswer);
				}
			}
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

	public AnswerDTO(Long pageId, Long viewId, List<Answer> answers, int status) {
		super();
		this.viewId = viewId;
		this.pageId = pageId;
		this.answers = answers;
		this.status = status;
	}

	/**
	 * 
	 */
}
