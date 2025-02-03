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

import sudo.configrator.dtos.DataLinkDTO;
import sudo.configrator.entities.DataLinkInfo;
import sudo.configrator.jparepositorys.DataLinkInfoRepository;
import sudo.configrator.services.DataLinkService;
import sudo.configrator.services.WebPageService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
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
	public ResultMapper deleteDataLink(Long id) {
		logger.info("getDataLink method called..." + id);
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
			logger.error("while getting error  on  getDataLink>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper setDataLink(DataLinkDTO dataLinkDTO) {
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
						DataLinkInfo newDataLinkInfo = dataLinkDTO.dataLinkDetailsDTO();
						dataLinkInfoRepository.saveAndFlush(newDataLinkInfo);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						DataLinkInfo exitDataLinkInfo = dataLinkInfoRepository.findById(dataLinkDTO.getId()).get();
						List changeHistoryList = exitDataLinkInfo.update(dataLinkDTO.dataLinkDetailsDTO());
						exitDataLinkInfo.setUpdateBy(resultMapper.getBy());
						exitDataLinkInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						dataLinkInfoRepository.saveAndFlush(exitDataLinkInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitDataLinkInfo.getId(),
											exitDataLinkInfo.getUpdateDate(), exitDataLinkInfo.getUpdateBy(),
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
			logger.error("while getting error  on  setDataLink>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getDataLinkByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataLinkInfo getDataLinkInfoByid(Long id) {
		return dataLinkInfoRepository.findById(id).get();
	}

	@Override
	public DataLinkInfo getDataLinkInfoByCode(String code) {
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
