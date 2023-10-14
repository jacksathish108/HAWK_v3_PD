package hawk.dtos;

import hawk.entities.UsersInfo;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class UserDetails {
	Long id;
	String name;
	String mobileNo;
	String email;
	String dob;
	String address;
	String profilePicUrl;
	int status;
	int userGroup;
	String role;
	
	public UserDetails(Long id,String name, String mobileno, String email, String profilepicurl,
			int usergroup, int userstatuscode) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNo = mobileno;
		this.email = email;
		this.profilePicUrl = profilepicurl;
		this.status = userstatuscode;
	}
	public UsersInfo UserDetails() {
		UsersInfo usersInfo = new UsersInfo();
		usersInfo.setId(this.id);
		usersInfo.setMobileNo(this.mobileNo);
		usersInfo.setName(this.name);
		usersInfo.setEmail(this.email);
		usersInfo.setProfilePicUrl(this.profilePicUrl);
		usersInfo.setStatus(this.status);
		usersInfo.setUserGroup(this.userGroup);
		return usersInfo;
	}
}
