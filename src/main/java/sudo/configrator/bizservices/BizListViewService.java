package sudo.configrator.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.configrator.dtos.ListViewDTO;
import sudo.configrator.entities.ListViewInfo;
import sudo.configrator.jparepositorys.ListViewInfoRepository;
import sudo.configrator.services.ListViewService;
import sudo.configrator.services.QuestionService;
import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;
import sudo.services.FieldUpdateHistoryService;
import sudo.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizListViewService implements ListViewService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizListViewService.class);

	@Autowired
	UsersService clientService;

	@Autowired
	ListViewInfoRepository listViewInfoRepository;
	@Autowired
	QuestionService questionService;

	@Autowired
	FieldUpdateHistoryService fieldUpdateHistoryService;
	ResultMapper resultMapper;

	@Override
	public ResultMapper getListView() {
		logger.info("getListView method called...");
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {
					List<ListViewDTO> listViewInfoList = new ArrayList<>();
					listViewInfoRepository.findAll().forEach(listViewInfo -> {
						listViewInfoList.add(new ListViewDTO(listViewInfo));
					});
					resultMapper.setResponceList(listViewInfoList);
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
			logger.error("while getting error  on  getListView>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper deleteListView(Long id) {
		logger.info("getListView method called..." + id);
		try {
			resultMapper = clientService.getuserSession();
			if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
					|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
				listViewInfoRepository.deleteById(id);
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				resultMapper.setMessage(id + EnMessages.RECORD_DELETED_MSG);
			} else {
				logger.info("qTagList>>Invalid session ........");
				resultMapper.setStatusCode(EnMessages.ACCESS_DENIED_STATUS);
				resultMapper.setMessage(EnMessages.ACCESS_DENIED_MSG);
			}

		} catch (Exception e) {
			logger.error("while getting error  on  getListView>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper setListView(ListViewDTO listViewDTO) {
		logger.info("setWebPage method called..." + listViewDTO);
		try {
			resultMapper = clientService.getuserSession();
			if (listViewDTO != null && resultMapper.isSessionStatus()) {
				if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
						|| HawkResources.PDADMIN.equals(resultMapper.getUserRole())) {
					boolean isExistRecord = (listViewInfoRepository.isExist(listViewDTO.getId()) == 0 ? false : true);
					if (!isExistRecord) {
						listViewDTO.setCreateBy(resultMapper.getBy());
						listViewDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
						ListViewInfo newListViewInfo = listViewDTO.listViewDetailsDTO();
						listViewInfoRepository.saveAndFlush(newListViewInfo);
						resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
						resultMapper.setMessage(EnMessages.ENTRY_SUCCESS_MSG);
					} else if (isExistRecord) {
						ListViewInfo exitListViewInfo = listViewInfoRepository.findById(listViewDTO.getId()).get();
						List changeHistoryList = exitListViewInfo.update(listViewDTO.listViewDetailsDTO());
						exitListViewInfo.setUpdateBy(resultMapper.getBy());
						exitListViewInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
						listViewInfoRepository.saveAndFlush(exitListViewInfo);
						if (!changeHistoryList.isEmpty() && changeHistoryList.size() > 0)
							fieldUpdateHistoryService
									.setFieldUpdateHistory(new FieldUpdateHistoryInfo(exitListViewInfo.getId(),
											exitListViewInfo.getUpdateDate(), exitListViewInfo.getUpdateBy(),
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
			logger.error("while getting error  on  setListView>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getListViewByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListViewInfo getListViewInfoByid(Long id) {
		return listViewInfoRepository.findById(id).get();
	}

	@Override
	public ListViewInfo getListViewInfoByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMapper getListViewByCode(String linkCode) {
		logger.info("getListViewByCode method called..." + linkCode);
		try {
			resultMapper = clientService.getuserSession();
			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */ {

					resultMapper
							.setResponceObject(new ListViewDTO(listViewInfoRepository.findByListViewCode(linkCode)));
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
			logger.error("while getting error  on  getListViewByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public ResultMapper getAllSelectQtags() {
		logger.info("getAllSelectQtags method called...");
		try {

			resultMapper = questionService.getAllQtag();
		} catch (Exception e) {
			logger.error("while getting error  on  getListViewByLinkCode>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	@Override
	public List<ListViewDTO> getListViewBySelectedQtags(List qTags) {
		logger.info("getListViewBySelectedQtags method called..." + qTags);
		try {
			resultMapper = clientService.getuserSession();

			if (resultMapper.isSessionStatus()) {
				/*
				 * if (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())||
				 * HawkResources.PDADMIN.equals(resultMapper.getUserRole()))
				 */
				{
					return listViewInfoRepository.getAnswersByListViewTargetQtags(qTags);
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
			logger.error("Error while getting getListViewBySelectedQtags: " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}

		return null;
	}

}
