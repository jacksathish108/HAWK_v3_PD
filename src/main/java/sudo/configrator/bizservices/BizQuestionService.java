package sudo.configrator.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.configrator.dtos.QuestionDTO;
import sudo.configrator.entities.QuestionInfo;
import sudo.configrator.jparepositorys.QuestionInfoRepository;
import sudo.configrator.services.QtagGeneratorService;
import sudo.configrator.services.QuestionService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizQuestionService implements QuestionService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizQuestionService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	QuestionInfoRepository questionInfoRepository;
	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	@Autowired
	QtagGeneratorService qtagGeneratorService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper setQuestion(QuestionDTO questionInfoDTO) {
		logger.info("setQuestion method called..." + questionInfoDTO);
		try {
			resultMapper = clientService.getuserSession();
			if (questionInfoDTO != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (questionInfoRepository.isExist(questionInfoDTO.getId(),
							questionInfoDTO.getQTag())) == 0 ? false : true;
					if (!isExistRecord) {
						questionInfoDTO.setCreateBy(resultMapper.getBy());
						questionInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						questionInfoDTO.setQTag(qtagGeneratorService.genarateQtag("Q_"));
						questionInfoRepository.saveAndFlush(questionInfoDTO.QuestionInfoDTO());
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						QuestionInfo exitQuestionInfo = questionInfoRepository.findByIdorQTag(questionInfoDTO.getId(),
								questionInfoDTO.getQTag());
						List changeHistoryList = exitQuestionInfo.update(questionInfoDTO.QuestionInfoDTO());
						exitQuestionInfo.setUpdateBy(resultMapper.getBy());
						exitQuestionInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						questionInfoRepository.saveAndFlush(exitQuestionInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitQuestionInfo.getId(),
											exitQuestionInfo.getUpdateDate(), exitQuestionInfo.getUpdateBy(),
											HawkResources.HISTORY_COMPONENT_PACKAGE, changeHistoryList));
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					}
				} else {
					resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
					resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
				}
			}

		} catch (Exception e) {
			logger.error("while getting error  on  setQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
	@Override
	public ResultMapper setQuestion(List<QuestionDTO> questionInfoDTOList) {
		logger.info("setQuestion method called..." + questionInfoDTOList);
		try {
			resultMapper = clientService.getuserSession();
			if (questionInfoDTOList != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					
					{
						questionInfoDTOList.forEach(questionInfoDTO->
						{
							questionInfoDTO.setCreateBy(resultMapper.getBy());
							questionInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
							questionInfoDTO.setQTag(qtagGeneratorService.genarateQtag("Q_"));
							questionInfoRepository.saveAndFlush(questionInfoDTO.QuestionInfoDTO());
							resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
							resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
							
						});
					}

				} else {
					resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
					resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
				}
			}

		} catch (Exception e) {
			logger.error("while getting error  on  setQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
	@Override
	public ResultMapper getQuestion() {
		logger.info("getQuestion method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<QuestionDTO> QuestionInfoList = new ArrayList<>();
					questionInfoRepository.findAll().forEach(QuestionInfo -> {
						QuestionInfoList.add(new QuestionDTO(QuestionInfo));
					});
					resultMapper.setResponceList(QuestionInfoList);
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
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper deleteQuestion(Long id) {
		logger.info("getQuestion method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
				questionInfoRepository.deleteById(id);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				resultMapper.setMessage(id + EnMessages.RECORD_DELETED_MSG);
			} else {
				logger.info("attendanceEntry>>Invalid session ........");
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public QuestionInfo getQuestionByid(Long id) {
		logger.info("getQuestionByid method called..." + id);
		try {
			if (resultMapper.isSessionStatus()) {

				return questionInfoRepository.findById(id).get();

			}

		} catch (Exception e) {
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return new QuestionInfo();
	}

	@Override
	public QuestionInfo getQuestionByQtag(String Qtag) {
		logger.info("getQuestionByQtag method called..." + Qtag);
		try {
			if (clientService.getuserSession().isSessionStatus()) {

				return questionInfoRepository.findByQtag(Qtag);
			}
		} catch (Exception e) {
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return new QuestionInfo();
	}

	@Override
	public ResultMapper getAllQtag() {
		logger.info("getAllQtag method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				Map<String, String> qTagMap = new HashMap<String, String>();
				questionInfoRepository.findAll().forEach(QuestionInfo -> {
					qTagMap.put(QuestionInfo.getQTag(), QuestionInfo.getName());
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
	public QuestionDTO getByQtag(String qTag) {
		QuestionDTO questionDTO = null;
		logger.info("getByQtag method called..." + qTag);
		try {
			if (clientService.getuserSession().isSessionStatus()) {

				questionDTO = new QuestionDTO(questionInfoRepository.findByQtag(qTag));
			}
		} catch (Exception e) {
			logger.error("while getting error  on  getByQtag>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return questionDTO;
	}

	@Override
	public ResultMapper getQtagsByElementType(String elementType) {
		logger.info("getAllOptionQtags method called...");
		try {
			resultMapper = clientService.getuserSession();
			
			if (resultMapper.isSessionStatus()) {
				Map<String, String> qTagMap = new HashMap<String, String>();
				questionInfoRepository.findByElementType(elementType).forEach(QuestionInfo -> {
					qTagMap.put(QuestionInfo.getQTag(), QuestionInfo.getName());
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

}
