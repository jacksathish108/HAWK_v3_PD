package hawk.configrator.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hawk.configrator.dtos.WebPageInfoDTO;
import hawk.configrator.entities.WebPageInfo;
import hawk.configrator.jparepositorys.WebPageInfoRepository;
import hawk.configrator.mapper.WebpageQuestionMapper;
import hawk.configrator.services.ViewService;
import hawk.configrator.services.WebPageService;
import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;
import hawk.services.FieldUpdateHistoryService;
import hawk.services.UsersService;
import hawk.utils.EnMessages;
import hawk.utils.HawkResources;

@Service
public class BizWebPageService implements WebPageService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizWebPageService.class);

	@Autowired
	UsersService clientService;
	@Autowired
	ViewService viewService;
	@Autowired
	WebPageInfoRepository webPageInfoRepository;
	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper setWebPage(WebPageInfoDTO webPageInfoDTO) {
		logger.info("setWebPage method called..." + webPageInfoDTO);
		try {
			resultMapper = clientService.getuserSession();
			if (webPageInfoDTO != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (webPageInfoRepository.isExist(webPageInfoDTO.getId(),
							webPageInfoDTO.getPageCode())) == 0 ? false : true;
					if (!isExistRecord) {
						webPageInfoDTO.setCreateBy(resultMapper.getBy());
						webPageInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						WebPageInfo newWebPage = webPageInfoDTO.WebPageDetailsDTO();

//						for (int i = 0; i < newWebPage.getApplicableViews().size(); i++) {
//							newWebPage.getApplicableViews().set(i,
//									viewService.getViewInfoByid(newWebPage.getApplicableViews().get(i).getId()));
//						}

						webPageInfoRepository.saveAndFlush(newWebPage);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						WebPageInfo exitWebPageInfo = webPageInfoRepository.findByIdorPageCode(webPageInfoDTO.getId(),
								webPageInfoDTO.getPageCode());
						List changeHistoryList = exitWebPageInfo.update(webPageInfoDTO.WebPageDetailsDTO());
						exitWebPageInfo.setUpdateBy(resultMapper.getBy());
						exitWebPageInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						
//						for (int i = 0; i < exitWebPageInfo.getApplicableViews().size(); i++) {
//							exitWebPageInfo.getApplicableViews().set(i,
//									viewService.getViewInfoByid(exitWebPageInfo.getApplicableViews().get(i).getId()));
//						}
						webPageInfoRepository.saveAndFlush(exitWebPageInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitWebPageInfo.getId(),
											exitWebPageInfo.getUpdateDate(), exitWebPageInfo.getUpdateBy(),
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
			logger.error("while getting error  on  setWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getWebPage() {
		logger.info("getWebPage method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<WebPageInfoDTO> WebPageInfoList = new ArrayList<>();
					webPageInfoRepository.findAll().forEach(WebPageInfo -> {
						WebPageInfoList.add(new WebPageInfoDTO(WebPageInfo));
					});

					resultMapper.setResponceList(WebPageInfoList);
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
			logger.error("while getting error  on  getWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	
	
	
	
	
	
	
	@Override
	public ResultMapper getPageCode() {
		logger.info("getWebPage method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<WebPageInfoDTO> WebPageInfoList = new ArrayList<>();

					 webPageInfoRepository.findPageOnly((long) 1).forEach(WebPageInfo -> {
					WebPageInfoList.add(new WebPageInfoDTO(WebPageInfo.get(0),WebPageInfo.get(1)));
					});

					resultMapper.setResponceList(WebPageInfoList);
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
			logger.error("while getting error  on  getWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public ResultMapper deleteWebPage(Long id) {
		logger.info("getWebPage method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
				webPageInfoRepository.deleteById(id);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				resultMapper.setMessage(id + EnMessages.RECORD_DELETED_MSG);
			} else {
				logger.info("attendanceEntry>>Invalid session ........");
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getWebPageByid(Long id) {
		logger.info("getWebPageByid method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {

				resultMapper.setResponceObject(new WebPageInfoDTO(webPageInfoRepository.findById(id).get()));
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			} else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public WebPageInfo getWebPageInfoByid(Long id) {
		logger.info("getWebPageByid method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {

				return webPageInfoRepository.findById(id).get();
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}

	@Override
	public ResultMapper getWebPageInfoByCode(String code) {
		logger.info("getWebPageInfoByCode method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					resultMapper.setResponceObject(webPageInfoRepository.findByCode(code));
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
			logger.error("while getting error  on  getWebPage>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

}
