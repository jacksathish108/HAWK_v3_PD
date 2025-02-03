package sudo.product.bizservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.product.dtos.ViewDTO;
import sudo.product.entities.ViewInfo;
import sudo.product.jparepositorys.ViewInfoRepository;
import sudo.product.services.QuestionService;
import sudo.product.services.ViewService;
import sudo.user.dtos.ResultMapper;
import sudo.user.services.FieldUpdateHistoryService;
import sudo.user.services.UsersService;
import sudo.utils.EnMessages;

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
	public ViewDTO getAllQuestionsByViewid(Long id) {
		logger.info("getAllQuestionsByViewid method called...");
		ViewDTO viewDTO =null;
		try {
			
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
						viewDTO=new ViewDTO(viewInfoRepository.getById(id));
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
		return viewDTO;
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

	@Override
	public ResultMapper getViewByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
