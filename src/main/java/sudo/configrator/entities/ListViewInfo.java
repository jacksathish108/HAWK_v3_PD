/*
 * 
 */
package sudo.configrator.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import sudo.configrator.dtos.ListViewDTO;
import sudo.utils.HawkResources;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
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
@Table(name = "listview_info")
@NamedNativeQuery(
    name = "findBySelectedQtags",
    query = "SELECT ListView_Code,Source_Qtag,Target_Qtag,Dependency_Condition FROM listview_info WHERE Source_Qtag IN (:targetQtags) AND STATUS = 1",
    resultSetMapping = "test11"
)
@SqlResultSetMapping(
    name = "test11",
    classes = @ConstructorResult(
        targetClass = ListViewDTO.class,
        columns = {
            @ColumnResult(name = "ListView_Code", type = String.class),
            @ColumnResult(name = "Source_Qtag", type = String.class),
            @ColumnResult(name = "Target_Qtag", type = String.class),
            @ColumnResult(name = "Dependency_Condition", type = String.class)
            // Change the type to the correct one if "Id" is not a String
        }
    )
)


@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class ListViewInfo implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -6119885575709702331L;

	/* COMMON FOR ALL START */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Update_Date")
    private Timestamp updateDate;

    @Column(name = "Create_Date")
    private Timestamp createDate;

    @Column(name = "Create_By")
    private String createBy;

    @Column(name = "Update_By")
    private String updateBy;
    /* COMMON FOR ALL END */

    @Column(name = "ListView_Code", unique = true)
    @NotNull(message = "ListView_Code is required")
    private String listViewCode;

    @Column(name = "Source_Qtag")
    @NotNull(message = "Source_Qtag is required")
    private String sourceQtag;

    @Column(name = "Target_Qtag")
    @NotNull(message = "Target_Qtag is required")
    private String targetQtag;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Status")
    @NotNull(message = "Status is required")
    private int status;

    @Column(name = "Dependency_Condition")
    private String dependencyCondition;

	public List update(ListViewInfo viewInfo) {
		List<Object> changeHistoryList = new ArrayList<>();

		if (!Objects.equals(this.updateDate, viewInfo.getUpdateDate())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateDate", updateDate, viewInfo.getUpdateDate()));
			this.updateDate = viewInfo.getUpdateDate();
		}
		if (!Objects.equals(this.updateBy, viewInfo.getUpdateBy())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("updateBy", updateBy, viewInfo.getUpdateBy()));
			this.updateBy = viewInfo.getUpdateBy();
		}

		if (!Objects.equals(this.listViewCode, viewInfo.getListViewCode())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("viewCode", listViewCode, viewInfo.getListViewCode()));
			this.listViewCode = viewInfo.getListViewCode();
		}

		if (!Objects.equals(this.description, viewInfo.getDescription())) {
			changeHistoryList
					.add(HawkResources.buildUpdateHistory("description", description, viewInfo.getDescription()));
			this.description = viewInfo.getDescription();
		}

		if (!Objects.equals(this.status, viewInfo.getStatus())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("status", status, viewInfo.getStatus()));
			this.status = viewInfo.getStatus();
		}

		if (!Objects.equals(this.sourceQtag, viewInfo.getSourceQtag())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("sourceQtag", sourceQtag, viewInfo.getSourceQtag()));
			this.sourceQtag = viewInfo.getSourceQtag();
		}

		if (!Objects.equals(this.targetQtag, viewInfo.getTargetQtag())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("targetQtag", targetQtag, viewInfo.getTargetQtag()));
			this.targetQtag = viewInfo.getTargetQtag();
		}

		if (!Objects.equals(this.dependencyCondition, viewInfo.getDependencyCondition())) {
			changeHistoryList.add(HawkResources.buildUpdateHistory("dependencyCondition", dependencyCondition,
					viewInfo.getDependencyCondition()));
			this.dependencyCondition = viewInfo.getDependencyCondition();
		}

		return changeHistoryList;
	}
}
