package sudo.user.dtos;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import sudo.user.entities.UsersInfo;
@Setter
@Getter
public class UserConfigDetailsDTO {
	/* COMMON FOR ALL START */
	private Long id;
	Timestamp updateDate;
	Timestamp createDate;
	String createBy;
	String updateBy;
	/* COMMON FOR ALL END */
	String name;
	String mobileNo;
	String email;
	String dob;
	String address;
	String profilePicUrl;
	int status;
	int userGroup;
	String role;
	String WebPageCode;
	Long WebPageId;
	String registrationType;
	
	public UserConfigDetailsDTO(UsersInfo user) {
		super();
		this.id = user.getId();
		this.createBy=user.getCreateBy();
		this.createDate=user.getCreateDate();
		this.name =user.getName();
		this.mobileNo = user.getMobileNo();
		this.email = user.getEmail();
		this.profilePicUrl = user.getProfilePicUrl();
		this.status = user.getStatus();
		this.userGroup = user.getUserGroup();
		
	}
	public UsersInfo UserConfigDetailsDTO() {
		UsersInfo usersInfo = new UsersInfo();
		usersInfo.setId(this.id);
		usersInfo.setMobileNo(this.mobileNo);
		usersInfo.setName(this.name);
		usersInfo.setEmail(this.email);
		if(this.id==null) 
		{
			usersInfo.setPwd(this.email);
			usersInfo.setCreateBy(this.createBy);
			usersInfo.setCreateDate(this.createDate);
			usersInfo.setRegistrationType(this.registrationType);
		}
		
		usersInfo.setProfilePicUrl(this.profilePicUrl);
		usersInfo.setStatus(this.status);
		usersInfo.setUserGroup(this.userGroup);
		return usersInfo;
	}
	/**
	 * 
	 */
	public UserConfigDetailsDTO() {
		super();
	}
}
