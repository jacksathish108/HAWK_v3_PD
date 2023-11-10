package hawk.configrator.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hawk.configrator.dtos.ViewDTO;
import hawk.configrator.entities.ViewInfo;
import hawk.configrator.jparepositorys.ViewInfoRepository;
import hawk.configrator.services.QuestionService;
import hawk.configrator.services.ViewService;
import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;
import hawk.services.FieldUpdateHistoryService;
import hawk.services.UsersService;
import hawk.utils.EnMessages;
import hawk.utils.HawkResources;

@Service
public class BizViewService implements ViewService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizViewService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	ViewInfoRepository viewInfoRepository;
	@Autowired
	QuestionService questionService;
	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper getView() {
		logger.info("getQuestion method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<ViewDTO> viewInfoList = new ArrayList<>();
					viewInfoRepository.findAll().forEach(viewInfo -> {
						viewInfoList.add(new ViewDTO(viewInfo));
					});
					resultMapper.setResponceList(viewInfoList);
					resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				} /*
					 * else { resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
					 * resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG); }
					 */
			} else {
				logger.info("qTagList>>Invalid session ....>...." + resultMapper.toString());
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
	public ResultMapper deleteView(Long id) {
		logger.info("getView method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
				viewInfoRepository.deleteById(id);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				resultMapper.setMessage(id + EnMessages.RECORD_DELETED_MSG);
			} else {
				logger.info("qTagList>>Invalid session ........");
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getView>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper setView(ViewDTO viewDTO) {
		logger.info("setWebPage method called..." + viewDTO);
		try {
			resultMapper = clientService.getuserSession();
			if (viewDTO != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (viewInfoRepository.isExist(viewDTO.getId()) == 0 ? false : true);
					if (!isExistRecord) {
						viewDTO.setCreateBy(resultMapper.getBy());
						viewDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						ViewInfo newViewInfo = viewDTO.viewDetailsDTO();

						for (int i = 0; i < newViewInfo.getApplicableQtagMap().size(); i++) {
							newViewInfo.getApplicableQtagMap().set(i,
									questionService.getQuestionByQtag(newViewInfo.getApplicableQtagMap().get(i).getQTag()));
						}

						viewInfoRepository.saveAndFlush(newViewInfo);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						 ViewInfo exitViewInfo = viewInfoRepository.findById(viewDTO.getId()).get();
						List changeHistoryList = exitViewInfo.update(viewDTO.viewDetailsDTO());
						exitViewInfo.setUpdateBy(resultMapper.getBy());
						exitViewInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						
						for (int i = 0; i < exitViewInfo.getApplicableQtagMap().size(); i++) {
							exitViewInfo.getApplicableQtagMap().set(i,
							questionService.getQuestionByQtag(exitViewInfo.getApplicableQtagMap().get(i).getQTag()));
							}
						viewInfoRepository.saveAndFlush(exitViewInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitViewInfo.getId(),
											exitViewInfo.getUpdateDate(), exitViewInfo.getUpdateBy(),
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
			logger.error("while getting error  on  setView>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getViewByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewInfo getViewInfoByid(Long id) {
		return viewInfoRepository.findById(id).get();
	}

	@Override
	public ViewInfo getViewInfoByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> qTagList() {
		logger.info("qTagList method called...");
		try {
			if (clientService.getuserSession().isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List viewInfoList = new ArrayList<>();
					viewInfoRepository.findAll().forEach(viewInfo -> {
						viewInfoList.add(viewInfo.getApplicableQtagMap());
					});
					resultMapper.setResponceList(viewInfoList);
					resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				} /*
					 * else { resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
					 * resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG); }
					 */
			} else {
				logger.info("qTagList>>Invalid session ....>...." + resultMapper.toString());
				resultMapper.setStatusCode(EnMessages.INVALID_SESSION_STATUS);
				resultMapper.setMessage(EnMessages.INVALID_SESSION_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}

	@Override
	public ResultMapper getAllViewName() {
		logger.info("getAllViewName method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				Map<String, String> qTagMap = new HashMap<String, String>();
				viewInfoRepository.findAll().forEach(viewInfo -> {
					qTagMap.put(String.valueOf(viewInfo.getId()), viewInfo.getName());
				});
				resultMapper.setResponceObject(qTagMap);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			} else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAllViewName>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

}
