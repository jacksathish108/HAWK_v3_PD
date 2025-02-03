/*
 * 
 */
package sudo.product.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import sudo.product.entities.ViewInfo;
import sudo.product.entities.WebPageInfo;

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
	private Map<String, ViewDTO> applicableViews = new HashMap<>();
	
	List<Long> applicableViewList;
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
			if (newWebPageInfo != null && newWebPageInfo.getApplicableViews() != null)
				newWebPageInfo.getApplicableViews().forEach(view -> {
					this.applicableViews.put(String.valueOf(view.getId()), new ViewDTO(view));
				});

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
		if (Objects.nonNull(applicableViewList)) {
			List<ViewInfo> views = new ArrayList<>();

			applicableViewList.forEach(k -> {
				views.add(new ViewDTO(k).viewDetailsDTO());
			});
			webPageInfo.setApplicableViews(views);
		}

		return webPageInfo;
	}

	public WebPageInfoDTO(String name, String pageCode) {
		super();
		this.name = name;
		this.pageCode = pageCode;
	}

	protected WebPageInfoDTO() {
		super();
	}
}
