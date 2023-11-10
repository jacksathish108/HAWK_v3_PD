package hawk.product.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hawk.configrator.services.QtagGeneratorService;
import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;
import hawk.product.dtos.AnswerDTO;
import hawk.product.entities.AnswerInfo;
import hawk.product.jparepositorys.AnswerInfoRepository;
import hawk.product.services.AnswerService;
import hawk.services.FieldUpdateHistoryService;
import hawk.services.UsersService;
import hawk.utils.EnMessages;
import hawk.utils.HawkResources;

@Service
public class BizAnswerService implements AnswerService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizAnswerService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	AnswerInfoRepository answerInfoRepository;
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
			if (answerInfoDTO != null && answerInfoDTO.getAnswers() != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.ADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (answerInfoRepository.isExist(answerInfoDTO.getId())) == 0 ? false : true;
					if (!isExistRecord) {
						answerInfoDTO.setCreateBy(resultMapper.getBy());
						answerInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						answerInfoRepository.saveAndFlush(answerInfoDTO.AnswerInfoDTO());
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);

					} else if (isExistRecord) {
						AnswerInfo exitAnswerInfo = answerInfoRepository.findById(answerInfoDTO.getId()).get();
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
						System.out.println(" ANS 0 :"+AnswerInfo.getAnswers());
						AnswerInfo.getAnswers().sort((o1, o2) -> o1.getQTag().compareTo( o2.getQTag()));
						System.out.println(" ANS 1 :"+AnswerInfo.getAnswers());
						AnswerInfoList.add(new AnswerDTO(AnswerInfo));
						System.out.println(" ANS 1 :"+AnswerInfo.getAnswers());
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
					System.out.println(" ANS 0 :"+answerInfo.getAnswers());
					answerInfo.getAnswers().sort((o1, o2) -> o1.getQTag().compareTo( o2.getQTag()));
					System.out.println(" ANS 1 :"+answerInfo.getAnswers());
					
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

}
