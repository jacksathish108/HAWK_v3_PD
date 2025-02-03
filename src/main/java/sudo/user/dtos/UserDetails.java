package sudo.user.dtos;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sudo.user.entities.UsersInfo;
@Setter
@Getter
@ToString
public class UserDetails implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7414616982532781167L;
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
	@Override
	public int hashCode() {
		return Objects.hash(address, dob, email, id, mobileNo, name, profilePicUrl, role, status, userGroup);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		return Objects.equals(address, other.address) && Objects.equals(dob, other.dob)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(mobileNo, other.mobileNo) && Objects.equals(name, other.name)
				&& Objects.equals(profilePicUrl, other.profilePicUrl) && Objects.equals(role, other.role)
				&& status == other.status && userGroup == other.userGroup;
	}
}
