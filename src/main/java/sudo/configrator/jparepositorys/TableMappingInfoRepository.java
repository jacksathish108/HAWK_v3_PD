/*
 * 
 */
package sudo.configrator.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sudo.configrator.entities.TableMappingInfo;
import sudo.configrator.entities.TableMappingInfo;

@Repository
public interface TableMappingInfoRepository extends JpaRepository<TableMappingInfo, Long> {

	@Query(value = "(SELECT datamaping.* FROM table_mapping_info datamaping WHERE DataMap_Code=:code)", nativeQuery = true) // Status=:status
	// and
	public TableMappingInfo findByCode(String code);

	@Query(value = "SELECT EXISTS(SELECT datamaping.* FROM table_mapping_info datamaping WHERE Id=:id)", nativeQuery = true) // Status=:status
	// and
	public long isExist(Long id);

	@Query(value = "SELECT datamaping.* FROM table_mapping_info datamaping WHERE  Status=0 ORDER BY ", nativeQuery = true) // Status=:status																														// and
	public List<TableMappingInfo> findAlldatamaping();

	@Query(value = "SELECT datamaping.* FROM table_mapping_info datamaping WHERE  Status=:status", nativeQuery = true) // Status=:status
// and
	public List<TableMappingInfo> findByStatus(Long status);

	@Query(value = "SELECT datamaping.* FROM table_mapping_info datamaping WHERE Id=:id or DataMap_Code=:code", nativeQuery = true) // Status=:status
	// and
	public TableMappingInfo findByIdorPageCode(Long id, String code);
	
	@Query(value = "SELECT datamaping.* FROM table_mapping_info datamaping WHERE DataMap_Codea=:code and  Status=1", nativeQuery = true) // Status=:status
	// and
	public TableMappingInfo findByTableMappingCode( String code);

	@Query(value = "SELECT datamaping.* FROM table_mapping_info datamaping WHERE Source_WebPageId =:sourcePageId AND Source_ViewId=:sourceViewId and  Status=:status", nativeQuery = true) // Status=:status
	// and
	public List<TableMappingInfo> findBySourceData(int status,Long sourcePageId, Long sourceViewId);

}
