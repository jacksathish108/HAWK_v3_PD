package sudo.bizservices;

import java.sql.Timestamp;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.jparepositorys.UsersInfoRepository;
import sudo.security.Provider;
import sudo.user.dtos.ResultMapper;
import sudo.user.dtos.UserDetails;
import sudo.user.entities.UsersInfo;
import sudo.user.services.UsersService;
import sudo.utils.EnMessages;
import sudo.utils.HawkResources;

@Service
public class BizUserService implements UsersService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizUserService.class);

	@Autowired
	private UsersInfoRepository usersInfoRepository;

	/** The now. */
	Timestamp now;
	//private Map<String,ResultMapper> userSessionMap;
	  @Autowired
	   private SessionData sessionData;
	public ResultMapper userLogout() {
		logger.info("userLogout method called....");
		ResultMapper resultMapper = new ResultMapper();
		try {
			// attendanceInfo_entry(String.valueOf(request.getSession().getAttribute("user_Id")),
			// 1);
			// request.getSession().removeAttribute("user_Id");
			// request.getSession().removeAttribute("data");
			// request.getSession().removeAttribute("user_Id");
			resultMapper.setView("user");
			resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
			logger.info("userLogout>>>resultMapper >>...." + resultMapper.toString());
			return resultMapper;
		} catch (Exception e) {
			logger.error("while getting error  on  userLogout>>>> " + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public ResultMapper attendanceEntry(int status, String hostIp, String sessionId) {
		logger.info("attendanceEntry method called....");
		ResultMapper resultMapper = new ResultMapper();
		try {

		} catch (Exception e) {
			logger.error("while getting error  on  attendanceEntry>>>> " + e.getMessage());
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			resultMapper.setMessage(e.getMessage());
		}
		return resultMapper;
	}

	public ResultMapper userRegestration(UsersInfo usersInfo) {
		logger.info("userRegestration method called...." + usersInfo);
		ResultMapper resultMapper = new ResultMapper();
		try {
			if (usersInfo == null || usersInfo.getName() == null) {
				resultMapper.setMessage("User mandatory  fields missing");
				resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
				logger.info("userRegestration>> mandatory  fields missing...." + usersInfo);
				return resultMapper;
			} else if (usersInfo.getRegistrationType().equals(Provider.LOCAL.toString())
					&& usersInfo.getMobileNo() == null
					|| usersInfoRepository.findByMobile(usersInfo.getMobileNo()) != null) {
				resultMapper.setMessage("User mobile Number already exists");
				resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
				logger.info("userRegestration>> mobile Number already exists...." + usersInfo);
				return resultMapper;
			}

			else if (usersInfo.getEmail() == null || usersInfoRepository.findByEmail(usersInfo.getEmail(), 0) != null) {
				resultMapper.setMessage("User Email already exists");
				resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
				logger.info("userRegestration>> User Email already exists...." + usersInfo);
				return resultMapper;
			} else if (usersInfo.getRegistrationType().equals(Provider.LOCAL.toString())) {
				usersInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				usersInfo.setRegistrationType(Provider.LOCAL.toString());
				usersInfo.setStatus(0);
				usersInfo.setUserGroup(0);
			}
			if (usersInfo != null) {
				usersInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				usersInfo = usersInfoRepository.save(usersInfo);
				setuserSession(usersInfo.getEmail());
				resultMapper.setView("/home");
				resultMapper.setStatusCode(EnMessages.SUCCESS_STATUS);
				// resultMapper.setUsersInfo(usersInfo);
				resultMapper.setMessage("User Regestration completed");
				logger.info("User Regestration completed...." + usersInfo);
				// sendEmail(usersInfo.getEmail(), "welcome to sudo fammily", "HAPPY");
				return resultMapper;
			} else {
				resultMapper.setMessage("User mandatory  fields missing");
				resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
				logger.info("userRegestration>> mandatory  fields missing...." + usersInfo);
				return resultMapper;
			}

		} catch (Exception e) {
			resultMapper.setMessage("while getting error  on  userRegestration");
			resultMapper.setStatusCode(EnMessages.ERROR_STATUS);
			logger.error("while getting error  on  userRegestration>>>> " + e.getMessage());
		}
		return resultMapper;
	}

	public static UserDetails toUserDetails(UsersInfo usersInfo) {

		if (usersInfo != null) {
			UserDetails user_details = new UserDetails(usersInfo.getId(), usersInfo.getName(), usersInfo.getMobileNo(),
					usersInfo.getEmail(), usersInfo.getProfilePicUrl(), usersInfo.getUserGroup(),
					usersInfo.getStatus());

			return user_details;
		}
		return null;
	}

	public UsersInfo updateuser(UsersInfo usersInfo) {
		logger.info("updateuser method called....>>" + usersInfo);
		return usersInfoRepository.save(usersInfo);
	}

	public void sendEmail(String toAddress, String msg, String subject) {
	}

	
	public ResultMapper getUserSession() {
		return sessionData.getUserSession();
	}
	
	public void setUserSession(ResultMapper userSession) {
		sessionData.setUserSession(userSession);
	}
	
	
	public ResultMapper setuserSession(String userName) {
		logger.info("setuserSession method called....>>" + userName);
		ResultMapper userSession = new ResultMapper();	
		try {
			UsersInfo user_Info = usersInfoRepository.findByUser(userName, 0);
			if (!Objects.isNull(user_Info)) {
				userSession.setUserDetails(toUserDetails(user_Info));
				userSession.setSessionStatus(true);
				userSession.setUserRole(HawkResources.getUserRole(user_Info.getUserGroup()));
				userSession.setBy(user_Info.getName() + "@" + user_Info.getMobileNo());
				//userSessionMap.put(sessionId, userSession);
				setUserSession(userSession);
				//return userSessionMap.get(sessionId);
				return getUserSession();
			}

		} catch (Exception e) {
			logger.error("while getting error  on  userRegestration>>>> " + e.getMessage());
		}
		return null;

	}

	public ResultMapper getPackgeDetails() {
		// TODO Auto-generated method stub
		return new ResultMapper();
	}
	@Override
	public ResultMapper getuserSession() {
		return getUserSession();
	}

	@Override
	public UsersInfo getuserInfo(String email) {
		logger.info("getuserInfo method called....>>" + email);
		return usersInfoRepository.findByUser(email, 0);
	}

	@Override
	public boolean isExist(String mobileNo, String email) {
		return (usersInfoRepository.isExistIdorMobileorEmail((long) 0, mobileNo, email)) == 0 ? false : true;
	}

	@Override
	public UsersInfo findByClientId(String clientId) {
		logger.info("getuserInfo method called....>>" + clientId);
		return usersInfoRepository.findByClientId(clientId);
	}


}
