package sudo.product.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.product.dtos.DataLinkDTO;
import sudo.product.entities.DataLinkInfo;
import sudo.product.jparepositorys.DataLinkInfoRepository;
import sudo.product.services.DataLinkService;
import sudo.product.services.WebPageService;
import sudo.user.dtos.ResultMapper;
import sudo.user.entities.FieldUpdateHistoryInfo;
import sudo.user.services.FieldUpdateHistoryService;
import sudo.user.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizDataLinkService implements DataLinkService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizDataLinkService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	DataLinkInfoRepository dataLinkInfoRepository;
	@Autowired
	WebPageService webPageService;

	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper getDataLink() {
		logger.info("getQuestion method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<DataLinkDTO> dataLinkInfoList = new ArrayList<>();
					dataLinkInfoRepository.findAll().forEach(dataLinkInfo -> {
						dataLinkInfoList.add(new DataLinkDTO(dataLinkInfo));
					});
					resultMapper.setResponceList(dataLinkInfoList);
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
	public List<String> qTagList() {
		logger.info("qTagList method called...");
		try {
			if (clientService.getuserSession().isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List dataLinkInfoList = new ArrayList<>();
					dataLinkInfoRepository.findAll().forEach(dataLinkInfo -> {
						dataLinkInfoList.add(dataLinkInfo.getQtagMap());
					});
					resultMapper.setResponceList(dataLinkInfoList);
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
	public ResultMapper getAllDataLinkName() {
		logger.info("getAllDataLinkName method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				Map<String, String> qTagMap = new HashMap<String, String>();
				dataLinkInfoRepository.findAll().forEach(dataLinkInfo -> {
					qTagMap.put(String.valueOf(dataLinkInfo.getId()), dataLinkInfo.getName());
				});
				resultMapper.setResponceObject(qTagMap);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			} else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getAllDataLinkName>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getDataLinkByLinkCode(String linkCode) {
		logger.info("getDataLinkByLinkCode method called..." + linkCode);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {

					resultMapper
							.setResponceObject(new DataLinkDTO(dataLinkInfoRepository.findByDataLinkCode(linkCode)));
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
			logger.error("while getting error  on  getDataLinkByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
	@Override
	public List<DataLinkDTO> getDataLinkByTargetViewId(Long targetViewId) {
		logger.info("getDataLinkByTargetViewId method called..." + targetViewId);
		try {
						List<DataLinkDTO> dataLinkInfoList = new ArrayList<>();
						dataLinkInfoRepository.findByTargetViewId(targetViewId).forEach(dataLinkInfo -> {
							dataLinkInfoList.add(new DataLinkDTO(dataLinkInfo));
						});
						return dataLinkInfoList;

		} catch (Exception e) {
			logger.error("while getting error  on  getDataLinkByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}
}
