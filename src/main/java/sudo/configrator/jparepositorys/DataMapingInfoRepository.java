/*
 * 
 */
package sudo.configrator.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sudo.configrator.entities.DataMapingInfo;
import sudo.configrator.entities.DataMapingInfo;

@Repository
public interface DataMapingInfoRepository extends JpaRepository<DataMapingInfo, Long> {

	@Query(value = "(SELECT datamaping.* FROM datamaping_info datamaping WHERE DataMap_Code=:code)", nativeQuery = true) // Status=:status
	// and
	public DataMapingInfo findByCode(String code);

	@Query(value = "SELECT EXISTS(SELECT datamaping.* FROM datamaping_info datamaping WHERE Id=:id)", nativeQuery = true) // Status=:status
	// and
	public long isExist(Long id);

	@Query(value = "SELECT datamaping.* FROM datamaping_info datamaping WHERE  Status=0 ORDER BY ", nativeQuery = true) // Status=:status																														// and
	public List<DataMapingInfo> findAlldatamaping();

	@Query(value = "SELECT datamaping.* FROM datamaping_info datamaping WHERE  Status=:status", nativeQuery = true) // Status=:status
// and
	public List<DataMapingInfo> findByStatus(Long status);

	@Query(value = "SELECT datamaping.* FROM datamaping_info datamaping WHERE Id=:id or DataMap_Code=:code", nativeQuery = true) // Status=:status
	// and
	public DataMapingInfo findByIdorPageCode(Long id, String code);
	
	@Query(value = "SELECT datamaping.* FROM datamaping_info datamaping WHERE DataMap_Codea=:code and  Status=1", nativeQuery = true) // Status=:status
	// and
	public DataMapingInfo findByDataMapingCode( String code);

	@Query(value = "SELECT datamaping.* FROM datamaping_info datamaping WHERE Source_WebPageId =:sourcePageId AND Source_ViewId=:sourceViewId and  Status=:status", nativeQuery = true) // Status=:status
	// and
	public List<DataMapingInfo> findBySourceData(int status,Long sourcePageId, Long sourceViewId);

}
