/*
 * 
 */
package sudo.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sudo.user.entities.FieldUpdateHistoryInfo;

@Repository
public interface FieldUpdateHistoryInfoRepository extends JpaRepository<FieldUpdateHistoryInfo, Long> {
	
	@Query(value = "SELECT updates.* FROM field_update_history_info updates WHERE  Module_Name = :moduleName and Record_Id=:id", nativeQuery = true) //Status=:status and
	public List<FieldUpdateHistoryInfo> findByHistorysUsingModuleandInd(String moduleName,Long id);
	@Query(value = "DELTE FROM field_update_history_info  WHERE  Module_Name = :moduleName and Record_Id=:id", nativeQuery = true) //Status=:status and
	public Long deleteByHistorysUsingModuleandInd(String moduleName,Long id);

}
