/*
 * 
 */
package hawk.configrator.dtos;

import java.sql.Timestamp;
import java.util.Objects;

import hawk.configrator.entities.WebPageInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebPageInfoDTO {

	/* COMMON FOR ALL START */
	private Long id;
	Timestamp updateDate;
	Timestamp createDate;
	String createBy;
	String updateBy;
	/* COMMON FOR ALL END */
	String name;
	String menuGroup;
	Integer tabOrder;
	String pageCode;
	String title;
	String description;
	int status;
	// List clientInfo;

	public WebPageInfoDTO(WebPageInfo newWebPageInfo) {
		if (Objects.nonNull(newWebPageInfo)) {
			this.id = newWebPageInfo.getId();
			this.createDate = newWebPageInfo.getCreateDate();
			this.createBy = newWebPageInfo.getCreateBy();
			if (newWebPageInfo.getUpdateDate() == null)
				this.updateDate = newWebPageInfo.getCreateDate();
			else
				this.updateDate = newWebPageInfo.getUpdateDate();
			if (newWebPageInfo.getUpdateBy() == null)
				this.updateBy = newWebPageInfo.getCreateBy();
			else
				this.updateBy = newWebPageInfo.getUpdateBy();

			this.menuGroup = newWebPageInfo.getMenuGroup();
			this.tabOrder = newWebPageInfo.getTabOrder();
			this.pageCode = newWebPageInfo.getPageCode();
			this.name = newWebPageInfo.getName();
			this.title = newWebPageInfo.getTitle();
			this.description = newWebPageInfo.getDescription();
			this.status = newWebPageInfo.getStatus();

		}
	}

	public WebPageInfo WebPageDetailsDTO() {
		WebPageInfo webPageInfo = new WebPageInfo();
		if (Objects.nonNull(id))
			webPageInfo.setId(id);
		if (Objects.nonNull(createBy))
			webPageInfo.setCreateBy(createBy);
		if (Objects.nonNull(createDate))
			webPageInfo.setCreateDate(createDate);
		if (Objects.nonNull(description))
			webPageInfo.setDescription(description);
		if (Objects.nonNull(menuGroup))
			webPageInfo.setMenuGroup(menuGroup);
		if (Objects.nonNull(tabOrder))
			webPageInfo.setTabOrder(tabOrder);
		if (Objects.nonNull(pageCode))
			webPageInfo.setPageCode(pageCode);
		if (Objects.nonNull(name))
			webPageInfo.setName(name);
		if (Objects.nonNull(title))
			webPageInfo.setTitle(title);
		if (Objects.nonNull(updateDate))
			webPageInfo.setUpdateDate(updateDate);
		if (Objects.nonNull(updateBy))
			webPageInfo.setUpdateBy(updateBy);
		if (Objects.nonNull(description))
			webPageInfo.setDescription(description);
		if (Objects.nonNull(status))
			webPageInfo.setStatus(status);
		return webPageInfo;
	}
}
