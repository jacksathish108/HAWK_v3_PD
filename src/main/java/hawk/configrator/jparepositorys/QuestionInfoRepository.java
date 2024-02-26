/*
 * 
 */
package hawk.configrator.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hawk.configrator.entities.QuestionInfo;

@Repository
public interface QuestionInfoRepository extends JpaRepository<QuestionInfo, Long> {

	@Query(value = "(SELECT question.* FROM question_info question WHERE QTag=:qTag)", nativeQuery = true) // Status=:status
																											// and
	public QuestionInfo findByQtag(String qTag);

	@Query(value = "SELECT EXISTS(SELECT question.* FROM question_info question WHERE Id=:id or QTag=:qTag)", nativeQuery = true) // Status=:status
																																	// and
	public long isExist(Long id, String qTag);

	@Query(value = "SELECT question.* FROM question_info question WHERE  Status=:status", nativeQuery = true) // Status=:status
																												// and
	public List<QuestionInfo> findByStatus(Long status);

	@Query(value = "SELECT question.* FROM question_info question WHERE  Element_Type=:elementType", nativeQuery = true) // Status=:status
	// and
	public List<QuestionInfo> findByElementType(String elementType);

	@Query(value = "SELECT question.* FROM question_info question WHERE Id=:id or QTag=:qTag", nativeQuery = true) // Status=:status
																													// and
	public QuestionInfo findByIdorQTag(Long id, String qTag);

}
