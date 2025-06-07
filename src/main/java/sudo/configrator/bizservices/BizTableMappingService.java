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

import sudo.configrator.dtos.TableMappingDTO;
import sudo.configrator.entities.TableMappingInfo;
import sudo.configrator.jparepositorys.TableMappingInfoRepository;
import sudo.configrator.services.TableMappingService;
import sudo.configrator.services.WebPageService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizTableMappingService implements TableMappingService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizTableMappingService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	TableMappingInfoRepository dataMapInfoRepository;
	@Autowired
	WebPageService webPageService;

	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper getTableMapping() {
		logger.info("getQuestion method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<TableMappingDTO> dataMapInfoList = new ArrayList<>();
					dataMapInfoRepository.findAll().forEach(dataMapInfo -> {
						dataMapInfoList.add(new TableMappingDTO(dataMapInfo));
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
			logger.error("while getting error  on  getTableMapping>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper deleteTableMapping(Long id) {
		logger.info("getTableMapping method called..." + id);
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
			logger.error("while getting error  on  getTableMapping>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper setTableMapping(TableMappingDTO dataMapDTO) {
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
						TableMappingInfo newTableMappingInfo = dataMapDTO.TableMappingDetailsDTO();
						dataMapInfoRepository.saveAndFlush(newTableMappingInfo);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						TableMappingInfo exitTableMappingInfo = dataMapInfoRepository.findById(dataMapDTO.getId()).get();
						List changeHistoryList = exitTableMappingInfo.update(dataMapDTO.TableMappingDetailsDTO());
						exitTableMappingInfo.setUpdateBy(resultMapper.getBy());
						exitTableMappingInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						dataMapInfoRepository.saveAndFlush(exitTableMappingInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitTableMappingInfo.getId(),
											exitTableMappingInfo.getUpdateDate(), exitTableMappingInfo.getUpdateBy(),
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
			logger.error("while getting error  on  setTableMapping>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getTableMappingByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableMappingInfo getTableMappingInfoByid(Long id) {
		return dataMapInfoRepository.findById(id).get();
	}

	@Override
	public TableMappingInfo getTableMappingInfoByCode(String code) {
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
						dataMapInfoList.add(dataMapInfo.getColumnMap());
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
	public ResultMapper getAllTableMappingName() {
		logger.info("getAllTableMappingName method called...");
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
			logger.error("while getting error  on  getAllTableMappingName>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getTableMappingBydataMapCode(String linkCode) {
		logger.info("getTableMappingByLinkCode method called..." + linkCode);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {

					resultMapper
							.setResponceObject(new TableMappingDTO(dataMapInfoRepository.findByTableMappingCode(linkCode)));
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
			logger.error("while getting error  on  getTableMappingByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
	@Override
	public ResultMapper getTableMappingBySourcePageIdandViewId(Long sourcePageId,Long sourceViewId) {
		logger.info("getTableMappingByTargetViewId method called..." + sourcePageId +" : "+sourceViewId);
		resultMapper = clientService.getuserSession();
		try {
						List<TableMappingDTO> dataMapInfoList = new ArrayList<>();
						dataMapInfoRepository.findBySourceData(1,sourcePageId,sourceViewId).forEach(dataMapInfo -> {
							dataMapInfoList.add(new TableMappingDTO(dataMapInfo));
						});
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setResponceList(dataMapInfoList);

		} catch (Exception e) {
			logger.error("while getting error  on  getTableMappingByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}
}
