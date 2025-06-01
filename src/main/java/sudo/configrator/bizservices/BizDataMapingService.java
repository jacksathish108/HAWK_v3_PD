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

import sudo.configrator.dtos.DataMapingDTO;
import sudo.configrator.entities.DataMapingInfo;
import sudo.configrator.jparepositorys.DataMapingInfoRepository;
import sudo.configrator.services.DataMapingService;
import sudo.configrator.services.WebPageService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizDataMapingService implements DataMapingService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizDataMapingService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	DataMapingInfoRepository dataMapInfoRepository;
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
					List<DataMapingDTO> dataMapInfoList = new ArrayList<>();
					dataMapInfoRepository.findAll().forEach(dataMapInfo -> {
						dataMapInfoList.add(new DataMapingDTO(dataMapInfo));
					});
					resultMapper.setResponceList(dataMapInfoList);
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
			logger.error("while getting error  on  getDataMaping>>>> " + e.getMessage());
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
				dataMapInfoRepository.deleteById(id);
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
	public ResultMapper setDataMaping(DataMapingDTO dataMapDTO) {
		logger.info("setWebPage method called..." + dataMapDTO);
		try {
			resultMapper = clientService.getuserSession();
			if (dataMapDTO != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (dataMapInfoRepository.isExist(dataMapDTO.getId()) == 0 ? false : true);
					if (!isExistRecord) {
						dataMapDTO.setCreateBy(resultMapper.getBy());
						dataMapDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						DataMapingInfo newDataMapingInfo = dataMapDTO.DataMapingDetailsDTO();
						dataMapInfoRepository.saveAndFlush(newDataMapingInfo);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						DataMapingInfo exitDataMapingInfo = dataMapInfoRepository.findById(dataMapDTO.getId()).get();
						List changeHistoryList = exitDataMapingInfo.update(dataMapDTO.DataMapingDetailsDTO());
						exitDataMapingInfo.setUpdateBy(resultMapper.getBy());
						exitDataMapingInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						dataMapInfoRepository.saveAndFlush(exitDataMapingInfo);
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
		return dataMapInfoRepository.findById(id).get();
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
					List dataMapInfoList = new ArrayList<>();
					dataMapInfoRepository.findAll().forEach(dataMapInfo -> {
						dataMapInfoList.add(dataMapInfo.getQtagMap());
					});
					resultMapper.setResponceList(dataMapInfoList);
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
			logger.error("while getting error  on  DataMapping qTagList>>>> " + e.getMessage());
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
				dataMapInfoRepository.findAll().forEach(dataMapInfo -> {
					qTagMap.put(String.valueOf(dataMapInfo.getId()), dataMapInfo.getName());
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
	public ResultMapper getDataMapingBydataMapCode(String linkCode) {
		logger.info("getDataMapingByLinkCode method called..." + linkCode);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {

					resultMapper
							.setResponceObject(new DataMapingDTO(dataMapInfoRepository.findByDataMapingCode(linkCode)));
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
	public ResultMapper getDataMapingBySourcePageIdandViewId(Long sourcePageId,Long sourceViewId) {
		logger.info("getDataMapingByTargetViewId method called..." + sourcePageId +" : "+sourceViewId);
		resultMapper = clientService.getuserSession();
		try {
						List<DataMapingDTO> dataMapInfoList = new ArrayList<>();
						dataMapInfoRepository.findBySourceData(1,sourcePageId,sourceViewId).forEach(dataMapInfo -> {
							dataMapInfoList.add(new DataMapingDTO(dataMapInfo));
						});
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setResponceList(dataMapInfoList);

		} catch (Exception e) {
			logger.error("while getting error  on  getDataMapingByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
}
