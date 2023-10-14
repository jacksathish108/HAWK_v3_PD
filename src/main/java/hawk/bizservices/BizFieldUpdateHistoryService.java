package hawk.bizservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;
import hawk.jparepositorys.FieldUpdateHistoryInfoRepository;
import hawk.services.FieldUpdateHistoryService;
import hawk.services.UsersService;
import hawk.utils.EnMessages;
import hawk.utils.HawkResources;

@Service
public class BizFieldUpdateHistoryService implements FieldUpdateHistoryService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizFieldUpdateHistoryService.class);

	@Autowired
	UsersService clientService;
	@Autowired
	FieldUpdateHistoryInfoRepository fieldUpdateHistoryInfoRepository;
	ResultMapper resultMapper;

	@Override
	public ResultMapper setFieldUpdateHistory(FieldUpdateHistoryInfo fieldUpdateHistoryInfo) {
	try {
		resultMapper = clientService.getuserSession();
			{
		fieldUpdateHistoryInfoRepository.saveAndFlush(fieldUpdateHistoryInfo);
		resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
		resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
			}
	} catch (Exception e) {
		logger.error("while getting error  on  setFieldUpdateHistory>>>> " + e.getMessage());
		resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
		resultMapper.setMessage(e.getMessage());
	}
	return resultMapper;
	}

	@Override
	public ResultMapper getFieldUpdateHistoryList(String module, Long id) {
		try {
			resultMapper = clientService.getuserSession();
			if(resultMapper.isSessionStatus() && (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.ADMIN.equals(resultMapper.getUserRole())))
				{
				resultMapper.setResponceList(fieldUpdateHistoryInfoRepository.findByHistorysUsingModuleandInd(module, id));	
			resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
				}
			else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}
		} catch (Exception e) {
			logger.error("while getting error  on  setFieldUpdateHistory>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper deleteFieldUpdateHistoryList(String module, Long id) {
		try {
			resultMapper = clientService.getuserSession();
			if(resultMapper.isSessionStatus() && (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.ADMIN.equals(resultMapper.getUserRole())))
				{
				resultMapper.setResponceList(fieldUpdateHistoryInfoRepository.findByHistorysUsingModuleandInd(module, id));	
			resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
				}
			else {
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}
		} catch (Exception e) {
			logger.error("while getting error  on  deleteFieldUpdateHistoryList>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

}
