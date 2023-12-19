/*
 * 
 */
package hawk.configrator.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import hawk.utils.HawkResources;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "WebPage_info")
@Setter
@Getter
@ToString
public class WebPageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8500393178695155459L;
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
	@Column(name = "Menu_Group")
	@NotNull(message = "Menu Code is required")
	String menuGroup;
	@Column(name = "Tab_Order")
	@NotNull(message = "Tab Order is required")
	Integer tabOrder;
	@Column(name = "Page_Code", unique = true)
	@NotNull(message = "Page Code is required")
	String pageCode;
	@Column(name = "Name")
	@NotNull(message = "Name is required")
	String name;
	@Column(name = "Title")
	@NotNull(message = "Title is required")
	String title;
	@Column(name = "Description")
	@NotNull(message = "Description is required")
	String description;
	@Column(name = "Status")
	@NotNull(message = "Status is required")
	int status;
	 //@OneToMany
	// @JoinColumn(name="Id", unique = false,insertable = true,updatable = true )
	//@ElementCollection
	//	private List<ViewInfo> applicableViews;
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinColumn(name = "ViewId", referencedColumnName = "id")
	@OrderBy(value = "Tab_Order asc ")
	private List<ViewInfo> applicableViews;

	public List update(WebPageInfo newWebPageInfo) {
		List<Object> changeHistoryList = new ArrayList<>();
		if (!Objects.equals(this.menuGroup, newWebPageInfo.getMenuGroup())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("menuGroup", menuGroup, newWebPageInfo.getMenuGroup()));
			this.menuGroup = newWebPageInfo.getMenuGroup();
		}
		if (!Objects.equals(this.tabOrder, newWebPageInfo.getTabOrder())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("tabOrder", tabOrder, newWebPageInfo.getTabOrder()));
			this.tabOrder = newWebPageInfo.getTabOrder();
		}

		if (!Objects.equals(this.pageCode, newWebPageInfo.getPageCode())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("pageCode", pageCode, newWebPageInfo.getPageCode()));
			this.pageCode = newWebPageInfo.getPageCode();
		}
		if (!Objects.equals(this.name, newWebPageInfo.getName())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("name", name, newWebPageInfo.getName()));
			this.name = newWebPageInfo.getName();
		}
		if (!Objects.equals(this.description, newWebPageInfo.getDescription())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("Description", description, newWebPageInfo.getDescription()));
			this.description = newWebPageInfo.getDescription();
		}
		if (!Objects.equals(this.status, newWebPageInfo.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("status", status, newWebPageInfo.getStatus()));
			this.status = newWebPageInfo.getStatus();
		}
		this.applicableViews = newWebPageInfo.getApplicableViews();

		return changeHistoryList;
	}
}
