/*
 * 
 */
package hawk.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hawk.utils.HawkResources;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// TODO: Auto_generated Javadoc
/**
 * The Class Hawk_Login.
 */
@Entity
@Table(name = "users_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class UsersInfo {

	/* COMMON FOR ALL START */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "Update_Date")
	Timestamp updateDate;
	@Column(name = "Create_Date")
	Timestamp createDate;
	@Column(name = "Create_By")
	String createBy;
	@Column(name = "Update_By")
	String updateBy;
	/* COMMON FOR ALL END */

	@NotNull(message = "User Group is required")
	@Column(name = "User_Group")
	int userGroup;
	@Column(name = "Client_Id")
	String clientId;
	@Column(name = "Name")
	@NotNull(message = "Name is required")
	String name;
	@Column(name = "Mobile_No", unique = true)
	@NotNull(message = "Mobile No  is required")
	String mobileNo;
	@Column(name = "Email", unique = true)
	@NotNull(message = "Email is required")
	String email;
	@Column(name = "Pwd")
	@NotNull(message = "Password is required")
	String pwd;
	@Column(name = "Profile_pic_url")
	String profilePicUrl;

	@Autowired
	public void setPwd(String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.pwd = encoder.encode(rawPassword);
	}

	@Column(name = "Registration_Type")
	String registrationType;
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;

	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "QuickView_Id", nullable = true)

	public List update(UsersInfo newUserInfo) {
		List<Object> changeHistoryList = new ArrayList<>();
		if (!Objects.equals(userGroup, newUserInfo.getUserGroup())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("UserGroup", userGroup, newUserInfo.getUserGroup()));
			userGroup = newUserInfo.getUserGroup();
		}
		if (!Objects.equals(clientId, newUserInfo.getClientId())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("ClientId", clientId, newUserInfo.getClientId()));
			clientId = newUserInfo.getClientId();
		}
		if (!Objects.equals(name, newUserInfo.getName())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("Name", name, newUserInfo.getName()));
			name = newUserInfo.getName();
		}
		if (!Objects.equals(mobileNo, newUserInfo.getMobileNo())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("MobileNo", mobileNo, newUserInfo.getMobileNo()));
			mobileNo = newUserInfo.getMobileNo();
		}

		if (!Objects.equals(email, newUserInfo.getEmail())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("Email", email, newUserInfo.getEmail()));
			email = newUserInfo.getEmail();
		}

		if (!Objects.equals(pwd, newUserInfo.getPwd()) && !Objects.isNull(newUserInfo.getPwd())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("pwd", pwd, newUserInfo.getPwd()));
			pwd = newUserInfo.getPwd();
		}

		if (!Objects.equals(profilePicUrl, newUserInfo.getProfilePicUrl())) {
			changeHistoryList.add(
					HawkResources.buildUpdateHistory("profilePicUrl", profilePicUrl, newUserInfo.getProfilePicUrl()));
			profilePicUrl = newUserInfo.getProfilePicUrl();
		}

		if (!Objects.equals(registrationType, newUserInfo.getRegistrationType())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("registrationType", registrationType,
					newUserInfo.getRegistrationType()));
			registrationType = newUserInfo.getRegistrationType();
		}
		if (!Objects.equals(status, newUserInfo.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("status", status, newUserInfo.getStatus()));
			status = newUserInfo.getStatus();
		}
		return changeHistoryList;
	}

}
