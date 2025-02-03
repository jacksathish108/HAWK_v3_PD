package sudo.configrator.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sudo.configrator.dtos.ListViewAnswerDTO;
import sudo.configrator.dtos.ListViewDTO;
import sudo.configrator.dtos.QuestionDTO;
import sudo.configrator.dtos.WebPageInfoDTO;
import sudo.configrator.entities.WebPageInfo;
import sudo.configrator.jparepositorys.WebPageInfoRepository;
import sudo.configrator.services.ListViewService;
import sudo.configrator.services.ViewService;
import sudo.configrator.services.WebPageService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.product.services.AnswerService;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizWebPageService implements WebPageService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizWebPageService.class);

	@Autowired
	UsersService clientService;
	@Autowired
	ViewService viewService;
	@Autowired
	AnswerService answerService;
	@Autowired
	ListViewService listViewService;
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
					webPageInfoRepository.findByStatus((long) 1).forEach(WebPageInfo -> {
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
						WebPageInfoList.add(new WebPageInfoDTO(WebPageInfo.get(0), WebPageInfo.get(1)));
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
	public WebPageInfoDTO getWebPageInfoByCode_1(String code) {
		logger.info("getWebPageInfoByCode method called...");
	try {
	    resultMapper = clientService.getuserSession();
	    if (resultMapper.isSessionStatus()) {
	        logger.info("getWebPageInfoByCode method called...");
	        return new WebPageInfoDTO(webPageInfoRepository.findByCode(code));
	    }
	}catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}
	    
	    
	    
	    
	@Override
	public ResultMapper getWebPageInfoByCode(String code) {logger.info("getWebPageInfoByCode method called...");
	try {
	    resultMapper = clientService.getuserSession();
	    if (resultMapper.isSessionStatus()) {
	        logger.info("getWebPageInfoByCode method called...");
	        WebPageInfoDTO webPageInfo = new WebPageInfoDTO(webPageInfoRepository.findByCode(code));

	        if (webPageInfo != null && !webPageInfo.getApplicableViews().isEmpty()) {
	            webPageInfo.getApplicableViews().values().forEach(view -> {
	                List<QuestionDTO> applicableQtagList = view.getApplicableQtagMap().values().stream()
	                        .filter(question -> question.getElementType()
	                                .equals(HawkResources.QUESTION_ELEMENTTYPE.select.name()))
	                        .collect(Collectors.toList());

	                List<ListViewDTO> listViewDTO = listViewService.getListViewBySelectedQtags(
	                        applicableQtagList.stream().map(QuestionDTO::getQTag).collect(Collectors.toList()));

	                List<String> targetQtagList = listViewDTO.stream().map(ListViewDTO::getTargetQtag)
	                        .collect(Collectors.toList());

	                List<ListViewAnswerDTO> viewAnswerDTOs = answerService.getAnswersByListViewTargetQtags(
	                        applicableQtagList.stream().map(QuestionDTO::getQTag).collect(Collectors.toList()));

	                Map<Long, List<ListViewAnswerDTO>> lsMap = viewAnswerDTOs.stream()
	                        .collect(Collectors.groupingBy(ListViewAnswerDTO::getAnsId, HashMap::new,
	                                Collectors.toList()));

	                view.getApplicableQtagMap().values().stream()
	                        .filter(question -> question.getElementType()
	                                .equals(HawkResources.QUESTION_ELEMENTTYPE.select.name()))
	                        .filter(question -> viewAnswerDTOs != null && lsMap.values() != null)
	                        .forEach(question -> {
	                            question.setOptions(question.getOptions());

	                            ListViewDTO lsvDTO = listViewDTO.stream()
	                                    .filter(listViewAns -> targetQtagList.contains(listViewAns.getTargetQtag()))
	                                    .findFirst().orElse(null);

	                            if (lsvDTO != null && lsvDTO.getDependencyCondition() != null) {
	                                Map<String, String> dependencyConditionMap = Arrays.stream(
	                                        lsvDTO.getDependencyCondition().split(","))
	                                        .map(String::trim)
	                                        .map(entry -> entry.split("="))
	                                        .collect(Collectors.toMap(
	                                                entry -> entry[1],
	                                                entry -> entry[0]
	                                        ));

	                                lsMap.values().forEach(listViewAns -> {
	                                    Map<String, String> attributes = new HashMap<>();
	                                    String options = "";

	                                    for (ListViewAnswerDTO s1 : listViewAns) {
	                                        if (s1.getSourceqTag().equals(lsvDTO.getTargetQtag())
	                                                && lsvDTO.getSourceQtag().equals(question.getQTag())) {
	                                            options = s1.getAnsValue();
	                                        } else if (dependencyConditionMap.containsKey(s1.getSourceqTag())) {
	                                            attributes.put(dependencyConditionMap.get(s1.getSourceqTag()),
	                                                    s1.getAnsValue());
	                                        }
	                                    }

	                                    try {
	                                        question.setOptions(question.getOptions() + "<option class='option' listView="
	                                                + new ObjectMapper().writeValueAsString(attributes)
	                                                + " value='" + options + "'>" + options + "</option>");
	                                    } catch (JsonProcessingException e) {
	                                        e.printStackTrace();
	                                    }
	                                });
	                            }
	                        });
	            });
	        }

	        resultMapper.setResponceObject(webPageInfo);
	        resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);

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
	public ResultMapper getAllWebPageCode() {
		logger.info("getAllWebPageCode method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {

					List<List<String>> allWebPageCode = webPageInfoRepository.getAllWebPageCode();
					Map<String, ArrayList> webpages = new HashMap<>();
					{
						allWebPageCode.forEach(object -> {
							if (webpages.containsKey(object.get(0))) {
								webpages.get(object.get(0)).add(object.get(1));
							} else {
								ArrayList views = new ArrayList<>();
								views.add(object.get(1));
								webpages.put(object.get(0), views);
							}

						}

						);

					}

					resultMapper.setResponceObject(webpages);
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
