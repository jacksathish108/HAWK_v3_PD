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

import hawk.configrator.dtos.DataMapingDTO;
import hawk.configrator.entities.DataMapingInfo;
import hawk.configrator.jparepositorys.DataMapingInfoRepository;
import hawk.configrator.services.DataMapingService;
import hawk.configrator.services.WebPageService;
import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;
import hawk.services.FieldUpdateHistoryService;
import hawk.services.UsersService;
import hawk.utils.EnMessages;
import hawk.utils.HawkResources;

@Service
public class BizDataMapingService implements DataMapingService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizDataMapingService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	DataMapingInfoRepository dataLinkInfoRepository;
	@Autowired
	WebPageService webPageService;

	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper getDataMaping() {
		logger.info("getQuestion method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<DataMapingDTO> dataLinkInfoList = new ArrayList<>();
					dataLinkInfoRepository.findAll().forEach(dataLinkInfo -> {
						dataLinkInfoList.add(new DataMapingDTO(dataLinkInfo));
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
	public ResultMapper deleteDataMaping(Long id) {
		logger.info("getDataMaping method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
				dataLinkInfoRepository.deleteById(id);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				resultMapper.setMessage(id + EnMessages.RECORD_DELETED_MSG);
			} else {
				logger.info("qTagList>>Invalid session ........");
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getDataMaping>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper setDataMaping(DataMapingDTO dataLinkDTO) {
		logger.info("setWebPage method called..." + dataLinkDTO);
		try {
			resultMapper = clientService.getuserSession();
			if (dataLinkDTO != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (dataLinkInfoRepository.isExist(dataLinkDTO.getId()) == 0 ? false : true);
					if (!isExistRecord) {
						dataLinkDTO.setCreateBy(resultMapper.getBy());
						dataLinkDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						DataMapingInfo newDataMapingInfo = dataLinkDTO.DataMapingDetailsDTO();
						dataLinkInfoRepository.saveAndFlush(newDataMapingInfo);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						DataMapingInfo exitDataMapingInfo = dataLinkInfoRepository.findById(dataLinkDTO.getId()).get();
						List changeHistoryList = exitDataMapingInfo.update(dataLinkDTO.DataMapingDetailsDTO());
						exitDataMapingInfo.setUpdateBy(resultMapper.getBy());
						exitDataMapingInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						dataLinkInfoRepository.saveAndFlush(exitDataMapingInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitDataMapingInfo.getId(),
											exitDataMapingInfo.getUpdateDate(), exitDataMapingInfo.getUpdateBy(),
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
			logger.error("while getting error  on  setDataMaping>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getDataMapingByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMapingInfo getDataMapingInfoByid(Long id) {
		return dataLinkInfoRepository.findById(id).get();
	}

	@Override
	public DataMapingInfo getDataMapingInfoByCode(String code) {
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
	public ResultMapper getAllDataMapingName() {
		logger.info("getAllDataMapingName method called...");
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
			logger.error("while getting error  on  getAllDataMapingName>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getDataMapingByLinkCode(String linkCode) {
		logger.info("getDataMapingByLinkCode method called..." + linkCode);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {

					resultMapper
							.setResponceObject(new DataMapingDTO(dataLinkInfoRepository.findByDataMapingCode(linkCode)));
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
			logger.error("while getting error  on  getDataMapingByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
	@Override
	public List<DataMapingDTO> getDataMapingByTargetViewId(Long targetViewId) {
		logger.info("getDataMapingByTargetViewId method called..." + targetViewId);
		try {
						List<DataMapingDTO> dataLinkInfoList = new ArrayList<>();
						dataLinkInfoRepository.findByTargetViewId(targetViewId).forEach(dataLinkInfo -> {
							dataLinkInfoList.add(new DataMapingDTO(dataLinkInfo));
						});
						return dataLinkInfoList;

		} catch (Exception e) {
			logger.error("while getting error  on  getDataMapingByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return null;
	}
}
