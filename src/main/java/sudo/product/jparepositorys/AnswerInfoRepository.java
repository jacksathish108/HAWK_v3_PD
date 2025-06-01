/*
 * 
 */
package sudo.product.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sudo.configrator.dtos.ListViewAnswerDTO;
import sudo.product.dtos.AnswersDTO;
import sudo.product.entities.AnswerInfo;

@Repository
public interface AnswerInfoRepository extends JpaRepository<AnswerInfo, Long>,AnswerInfoRepositoryCustom  {

	// @Procedure(name = ":procedureName")
	  //  String generateCustomID( String procedureName);

	@Query(value = "(SELECT answer.* FROM answer_info answer WHERE QTag=:qTag and Delete_Status !=1)", nativeQuery = true) // Status=:status
																										// and
	public AnswerInfo findByQtag(String qTag);

	@Query(value = "(SELECT answer.* FROM answer_info answer WHERE View_Id=:viewId and Page_Id=:pageId and Delete_Status !=1)", nativeQuery = true) // Status=:status
	// and
	public List<AnswerInfo> findAnswersByViewId(Long pageId, Long viewId);

	@Query(value = "SELECT EXISTS(SELECT answer.* FROM answer_info answer WHERE Id=:id and Delete_Status !=1)", nativeQuery = true) // Status=:status
																												// and
	public long isExist(Long id);

	@Query(value = "SELECT answer.* FROM answer_info answer WHERE  Status=:status", nativeQuery = true) // Status=:status
																										// and
	public List<AnswerInfo> findByStatus(Long status);

	@Query(value = "SELECT answer.* FROM answer_info answer WHERE Id=:id or QTag=:qTag and Delete_Status !=1", nativeQuery = true) // Status=:status
																												// and
	public AnswerInfo findByIdorQTag(Long id, Long qTag);
	
	
	@Modifying
    @Transactional
    @Query("UPDATE AnswerInfo ai SET ai.deleteStatus = 1 WHERE ai.id = :id")
void invalidateById(Long id);

//	@Query(value = "SELECT new sudo.product.dtos.AnswersDTO.AnswersDTO(answerInfo_Id,qTag,ansValue) FROM answers WHERE qTag IN(:qTags) AND ansValue IN (:values) ", nativeQuery = true) // Status=:status
//	// and
//	public List<AnswersDTO> getUniqueQuestionsValues(String qTags, String values);

	@Query(name = "getUniqueQuestionAnswers", nativeQuery = true)
	List<AnswersDTO> getUniqueQuestionsValues( List qTags, List ansValues);

	@Query(name = "getAnswersByQtag", nativeQuery = true)
	List<AnswersDTO> getAnswersByQtag( String qTags);
	
	
	@Query(name = "getAnswersByListViewTargetQtags", nativeQuery = true)
	List<ListViewAnswerDTO> getAnswersByListViewTargetQtags( List targetQtags);
	
	
	
	
}
