/*
 * 
 */
package hawk.configrator.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hawk.configrator.dtos.ListViewAnswerDTO;
import hawk.configrator.dtos.ListViewDTO;
import hawk.configrator.entities.ListViewInfo;
import hawk.configrator.entities.WebPageInfo;

@Repository
public interface ListViewInfoRepository extends JpaRepository<ListViewInfo, Long> {

	@Query(value = "(SELECT views.* FROM ListView_info views WHERE Page_Code=:code)", nativeQuery = true) // Status=:status
	// and
	public WebPageInfo findByCode(String code);

	@Query(value = "SELECT EXISTS(SELECT views.* FROM ListView_info views WHERE Id=:id)", nativeQuery = true) // Status=:status
	// and
	public long isExist(Long id);

	@Query(value = "SELECT views.* FROM ListView_info views WHERE  Status=0 ORDER BY ", nativeQuery = true) // Status=:status																														// and
	public List<ListViewInfo> findAllViews();

	@Query(value = "SELECT views.* FROM ListView_info views WHERE  Status=:status", nativeQuery = true) // Status=:status
// and
	public List<WebPageInfo> findByStatus(Long status);

	@Query(value = "SELECT views.* FROM ListView_info views WHERE Id=:id or Page_Code=:code", nativeQuery = true) // Status=:status
	// and
	public WebPageInfo findByIdorPageCode(Long id, String code);
	
	@Query(value = "SELECT datalinks.* FROM ListView_info datalinks WHERE Link_Code=:code and  Status=1", nativeQuery = true) // Status=:status
	// and
	public ListViewInfo findByListViewCode( String code);

	@Query(name = "findBySelectedQtags", nativeQuery = true)
	List<ListViewDTO> getAnswersByListViewTargetQtags( List targetQtags);



}
