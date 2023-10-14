package hawk.services;

import hawk.dtos.ResultMapper;
import hawk.entities.UsersInfo;

public interface UsersService {

	ResultMapper getPackgeDetails();

	ResultMapper attendanceEntry(int status, String hostIp, String sessionId);

	ResultMapper userLogout();

	ResultMapper userRegestration(UsersInfo userInfo);

	ResultMapper setuserSession(String userName);

	ResultMapper getuserSession();

	UsersInfo getuserInfo(String email);
	UsersInfo findByClientId(String clientId);
	
	boolean isExist(String mobileNo,String email);

	UsersInfo updateuser(UsersInfo userInfo);

	void sendEmail(String toAddress, String msg, String subject);

}
