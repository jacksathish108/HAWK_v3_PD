package sudo.product.bizservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.product.dtos.ListViewDTO;
import sudo.product.entities.ListViewInfo;
import sudo.product.jparepositorys.ListViewInfoRepository;
import sudo.product.services.ListViewService;
import sudo.product.services.QuestionService;
import sudo.user.dtos.ResultMapper;
import sudo.user.entities.FieldUpdateHistoryInfo;
import sudo.user.services.FieldUpdateHistoryService;
import sudo.user.services.UsersService;
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
			logger.error("while getting error  on  getQuestion>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
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
