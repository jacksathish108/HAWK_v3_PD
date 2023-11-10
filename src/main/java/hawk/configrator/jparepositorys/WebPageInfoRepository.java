/*
 * 
 */
package hawk.configrator.jparepositorys;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hawk.configrator.entities.WebPageInfo;

@Repository
public interface WebPageInfoRepository extends JpaRepository<WebPageInfo, Long> {

	@Query(value = "(SELECT WebPages.* FROM webPage_info WebPages WHERE Page_Code=:code)", nativeQuery = true) // Status=:status
																												// and
	public WebPageInfo findByCode(String code);

	@Query(value = "SELECT EXISTS(SELECT webpage.* FROM webPage_info webpage WHERE Id=:id or Page_Code=:code)", nativeQuery = true) // Status=:status
																																	// and
	public long isExist(Long id, String code);
//	@Query(value = "SELECT webpage.* FROM package_info webpage WHERE   Status=0 and Applicable_Branch IN(null,:applicable)", nativeQuery = true) //Status=:status and
//	public List<WebPageInfo> findByApplicable(String applicable);

	@Query(value = "SELECT webpage.* FROM webPage_info webpage WHERE  Status=:status", nativeQuery = true) // Status=:status																								// and
	public List<WebPageInfo> findByStatus(Long status);

	@Query(value = "SELECT webpage.Name, webpage.Page_Code  FROM webPage_info webpage WHERE  Status=:status", nativeQuery = true) // Status=:status																								// and
	public List<List<String>> findPageOnly(Long status);
	
	
	
	
	@Query(value = "SELECT webpage.* FROM webPage_info webpage WHERE Id=:id or Page_Code=:code", nativeQuery = true) // Status=:status
																														// and
	public WebPageInfo findByIdorPageCode(Long id, String code);

}
