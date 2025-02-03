package sudo.product.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.configrator.dtos.ListViewAnswerDTO;
import sudo.configrator.dtos.QuestionDTO;
import sudo.configrator.dtos.ViewDTO;
import sudo.configrator.services.DataLinkService;
import sudo.configrator.services.QtagGeneratorService;
import sudo.configrator.services.QuestionService;
import sudo.configrator.services.ViewService;
import sudo.configrator.services.WebPageService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.product.dtos.AnswerDTO;
import sudo.product.dtos.AnswersDTO;
import sudo.product.entities.AnswerInfo;
import sudo.product.jparepositorys.AnswerInfoRepository;
import sudo.product.services.AnswerService;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizAnswerService implements AnswerService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizAnswerService.class);

	@Autowired
	UsersService clientService;
	@Autowired
	ViewService viewService;
	@Autowired
	AnswerInfoRepository answerInfoRepository;
	@Autowired
	QuestionService questionService;
	@Autowired
	DataLinkService dataLinkService;
	@Autowired
	WebPageService webPageService;
	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	@Autowired
	QtagGeneratorService qtagGeneratorService;
	ResultMapper resultMapper;

	@Override

	public ResultMapper setAnswer(Map answers) {
		logger.info("setAnswer method called..." + answers);
		try {
			AnswerDTO answerInfoDTO = new AnswerDTO(answers);
			resultMapper = clientService.getuserSession();

			ViewDTO viewDTO = viewService.getAllQuestionsByViewid(answerInfoDTO.getViewId());
			viewDTO.setApplicableQtagList(new ArrayList<>());
			viewDTO.getApplicableQtagMap().values().forEach(question -> {
				if (question != null && question.getUnique() != null && question.getUnique() == 1)
					viewDTO.getApplicableQtagList().add(question.getQTag());
			});

			List qTags = new ArrayList<>();
			List ansValues = new ArrayList<>();

			answerInfoDTO.getAnswers().forEach(answer -> {
				if (answer != null && answer.getQTag() != null && viewDTO != null
						&& viewDTO.getApplicableQtagList().contains(answer.getQTag())) {
					qTags.add(answer.getQTag());
					ansValues.add(answer.getAnsValue());
				}
			});

			List<AnswersDTO> answersDTOList = answerInfoRepository.getUniqueQuestionsValues(qTags, ansValues);
			if (answerInfoDTO != null && answerInfoDTO.getAnswers() != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.ADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (answerInfoRepository.isExist(answerInfoDTO.getId())) == 0 ? false : true;
					if (!isExistRecord) {

						if (answersDTOList != null && !answersDTOList.isEmpty()) {
							resultMapper.setStatusCode(EnMessages.ALREDYEXIST_REQUEST);
							QuestionDTO questionDTO = questionService.getByQtag(answersDTOList.get(0).getQTag());
							resultMapper.setMessage(EnMessages.ALREDYEXIST_REQUEST_MSG + " : "
									+ ((questionDTO != null) ? questionDTO.getName().toString() : "") + " : "
									+ answersDTOList.get(0).getAnsValue());
							return resultMapper;
						}

						answerInfoDTO.setCreateBy(resultMapper.getBy());
						answerInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						answerInfoRepository.saveAndFlush(answerInfoDTO.AnswerInfoDTO());
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);

					} else if (isExistRecord) {
						AnswerInfo exitAnswerInfo = answerInfoRepository.findById(answerInfoDTO.getId()).get();

						List<AnswersDTO> ansList = answersDTOList.stream()
								.filter(ans -> (!ans.getAnswerInfo_Id().equals(exitAnswerInfo.getId())))
								.collect(Collectors.toList());
						if (ansList != null && !ansList.isEmpty()) {
							resultMapper.setStatusCode(EnMessages.ALREDYEXIST_REQUEST);
							QuestionDTO questionDTO = questionService.getByQtag(ansList.get(0).getQTag());
							resultMapper.setMessage(EnMessages.ALREDYEXIST_REQUEST_MSG + " : "
									+ ((questionDTO != null) ? questionDTO.getName().toString() : "") + " : "
									+ answersDTOList.get(0).getAnsValue());
							return resultMapper;
						}

						List changeHistoryList = exitAnswerInfo.update(answerInfoDTO.AnswerInfoDTO());
						exitAnswerInfo.setUpdateBy(resultMapper.getBy());
						exitAnswerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						answerInfoRepository.saveAndFlush(exitAnswerInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitAnswerInfo.getId(),
											exitAnswerInfo.getUpdateDate(), exitAnswerInfo.getUpdateBy(),
											HawkResources.HISTORY_COMPONENT_ANSWER, changeHistoryList));
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					}
				} else {
					resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
					resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
				}
			}
			updateDataLinkAnswers(viewDTO.getId());

		} catch (Exception e) {
			logger.error("while getting error  on  setAnswer>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getAnswer() {
		logger.info("getAnswer method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<AnswerDTO> AnswerInfoList = new ArrayList<>();
					answerInfoRepository.findAll().forEach(AnswerInfo -> {
						AnswerInfo.getAnswers().sort((o1, o2) -> o1.getQTag().compareTo(o2.getQTag()));
						AnswerInfoList.add(new AnswerDTO(AnswerInfo));
					});

					resultMapper.setResponceList(AnswerInfoList);
					resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				} /*
					 * else { resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
					 * resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG); }
					 */
			} else {
				logger.info("attendanceEntry>>Invalid session ....>...." + resultMapper.toString());
				resultMapper.setStatusCode(EnMessages.INVALID_SESSION_STATUS);
				resultMapper.setMessage(EnMessages.INVALID_SESSION_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAnswer>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper deleteAnswer(Long id) {
		logger.info("getAnswer method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())
					|| HawkResources.ADMIN.equals(resultMapper.getUserRole())) {
				answerInfoRepository.deleteById(id);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				resultMapper.setMessage(id + EnMessages.RECORD_DELETED_MSG);
			} else {
				logger.info("attendanceEntry>>Invalid session ........");
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAnswer>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getAnswersByViewId(Long pageId, Long viewId) {
		logger.info("getAnswersByViewId method called...pageId: " + pageId + " :viewId: " + viewId);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				List<AnswerDTO> AnswerInfoList = new ArrayList<>();
				answerInfoRepository.findAnswersByViewId(pageId, viewId).forEach(answerInfo -> {
					answerInfo.getAnswers().sort((o1, o2) -> o1.getQTag().compareTo(o2.getQTag()));
					AnswerInfoList.add(new AnswerDTO(answerInfo));
				});
				resultMapper.setResponceList(AnswerInfoList);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			} else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAnswersByViewId>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public List<AnswerDTO> getAnswerListsByViewId(Long pageId, Long viewId) {
		logger.info("getAnswersByViewId method called...pageId: " + pageId + " :viewId: " + viewId);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				List<AnswerDTO> AnswerInfoList = new ArrayList<>();
				answerInfoRepository.findAnswersByViewId(pageId, viewId).forEach(answerInfo -> {
					answerInfo.getAnswers().sort((o1, o2) -> o1.getQTag().compareTo(o2.getQTag()));
					AnswerInfoList.add(new AnswerDTO(answerInfo));
				});
				return AnswerInfoList;
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAnswersByViewId>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}

	@Override
	public ResultMapper getAnswersByQtag(String Qtag) {
		logger.info("getAnswersByQtag method called...");
		try {
			resultMapper = clientService.getuserSession();

			if (resultMapper.isSessionStatus()) {
				Map<String, String> qTagMap = new HashMap<String, String>();
				answerInfoRepository.getAnswersByQtag(Qtag).forEach(QuestionInfo -> {
					qTagMap.put(QuestionInfo.getQTag(), QuestionInfo.getAnsValue());
				});
				resultMapper.setResponceObject(qTagMap);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			} else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAllQtag>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public List<ListViewAnswerDTO> getAnswersByListViewTargetQtags(List targetQtags) {
		logger.info("getAnswersByListView method called...");
		try {
			return answerInfoRepository.getAnswersByListViewTargetQtags(targetQtags);
		} catch (Exception e) {
			logger.error("while getting error  on  getAllQtag>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}

	public void updateDataLinkAnswers(Long viewId) {
		try {
			resultMapper = clientService.getuserSession();
			dataLinkService.getDataLinkByTargetViewId(viewId).forEach(dataLinkDTO -> {

				List<AnswerDTO> answerDTOList = getAnswerListsByViewId(
						webPageService.getWebPageInfoByCode_1(dataLinkDTO.getSourceWebPageCode()).getId(),
						Long.valueOf(dataLinkDTO.getSourceViewId()));
				
				answerDTOList.forEach(answerDTO -> {
					JSONObject jsonObj = new JSONObject(dataLinkDTO.getQtagMap());
					answerDTO.setStatus(jsonObj.getInt("statusCode"));
					answerDTO.setUpdateBy(resultMapper.getBy());
					answerDTO.setUpdateDate(new Timestamp(System.currentTimeMillis()));
					answerDTO.setDiscription(jsonObj.getString("description"));
					answerInfoRepository.saveAndFlush(answerDTO.AnswerInfoDTO());
				});
			});

		} catch (Exception e) {
			logger.error("while getting error  on  processDataLinkAnswers>>>> " + e.getMessage());
		}

	}
}
