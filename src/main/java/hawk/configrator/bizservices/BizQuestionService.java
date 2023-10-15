package hawk.configrator.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hawk.configrator.dtos.QuestionDTO;
import hawk.configrator.entities.QuestionInfo;
import hawk.configrator.jparepositorys.QuestionInfoRepository;
import hawk.configrator.services.QtagGeneratorService;
import hawk.configrator.services.QuestionService;
import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;
import hawk.services.FieldUpdateHistoryService;
import hawk.services.UsersService;
import hawk.utils.EnMessages;
import hawk.utils.HawkResources;

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
	public ResultMapper getQuestionByid(Long id) {
		logger.info("getQuestionByid method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {

				resultMapper.setResponceObject(new QuestionDTO(questionInfoRepository.findById(id).get()));
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			} else {
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
	public QuestionDTO getQuestionByQtag(String Qtag) {
		logger.info("getQuestionByQtag method called..." + Qtag);
		try {
			if (clientService.getuserSession().isSessionStatus()) {

				return new QuestionDTO(questionInfoRepository.findByQtag(Qtag));
			}
		} catch (Exception e) {
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}

}
