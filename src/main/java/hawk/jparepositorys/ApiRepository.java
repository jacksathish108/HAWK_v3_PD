/*
 * 
 */
package hawk.jparepositorys;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hawk.entities.UsersInfo;
import hawk.utils.HawkResources;

// TODO: Auto-generated Javadoc
/**
 * The Interface Management_Info_Repository.
 */
@Repository
public interface ApiRepository extends JpaRepository<UsersInfo, Long> {

	@Query(value = "SELECT COUNT(*) FROM users_info WHERE QuickView_Id IS NOT NULL AND STATUS=0 ", nativeQuery = true)
	public Long findAllActiveClients();

	@Query(value = "SELECT COUNT(*) FROM users_info WHERE QuickView_Id IS NOT NULL ", nativeQuery = true)
	public Long findAllClients();

	@Query(value = "SELECT COUNT(*) FROM users_info WHERE QuickView_Id IS NOT NULL AND STATUS=0 AND Branch_Id=:branchId ", nativeQuery = true)
	public Long findActiveClientsByBranch(Long branchId);

	@Query(value = "SELECT COUNT(*) FROM users_info WHERE QuickView_Id IS NOT NULL AND Branch_Id=:branchId ", nativeQuery = true)
	public Long findAllClientsByBranch(Long branchId);

	@Query(value = "SELECT COUNT(*) FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id   WHERE USER.Status=0 AND quickview.WorkoutChart_Id IS NULL ", nativeQuery = true)
	public Long findWithoutWorkoutChartClientCount();

	@Query(value = "SELECT COUNT(*) FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id   WHERE USER.Status=0 AND USER.Branch_Id=:branchId AND quickview.WorkoutChart_Id IS NULL ", nativeQuery = true)
	public Long findWithoutWorkoutChartClientCount(Long branchId);

	@Query(value = "SELECT COUNT(*) FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id   WHERE USER.Status=0 AND DATEDIFF(quickview.Expiry_Date, CURDATE())< :renewalAlert ", nativeQuery = true)
	public Long findRenewalClientCount(int renewalAlert);

	@Query(value = "SELECT COUNT(*) FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id   WHERE USER.Status=0 AND USER.Branch_Id=:branchId AND DATEDIFF(quickview.Expiry_Date, CURDATE())< :renewalAlert ", nativeQuery = true)
	public Long findRenewalClientCount(int renewalAlert, Long branchId);

	@Query(value = "SELECT CONCAT (CONCAT(COUNT(*),\"-\") ,SUM(paymenthistory.PendingDue)) AS pendingDue FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id INNER JOIN payment_history paymenthistory ON quickview.Payment_History_Id=paymenthistory.id  WHERE USER.Status=0 AND  paymenthistory.PendingDue>:minVal", nativeQuery = true)
	public String findByPendingDueCount(Long minVal);

	@Query(value = "SELECT CONCAT (CONCAT(COUNT(*),\"-\") ,SUM(paymenthistory.PendingDue)) AS pendingDue FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id INNER JOIN payment_history paymenthistory ON quickview.Payment_History_Id=paymenthistory.id  WHERE USER.Status=0 AND USER.Branch_Id=:branchId AND paymenthistory.PendingDue>:minVal", nativeQuery = true)
	public String findByPendingDueCount(Long minVal, Long branchId);

	@Query(value = "(SELECT COUNT(*) FROM branch_info branchs WHERE status=0)", nativeQuery = true) // Status=:status
																									// and
	public Long findByBranches();

	@Query(value = "(SELECT Branch_Code FROM branch_info branchs WHERE status=0)", nativeQuery = true) // Status=:status
																										// and
	public List<?> getActiveBranchCodes();

	@Query(value = "SELECT COUNT(*) FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id\r\n"
			+ " INNER JOIN  feedback_info feedback WHERE USER.Status=0 AND  DATEDIFF(CURDATE(),quickview.Create_Date)>:difDay  \r\n"
			+ " AND (quickview.Feedback_Id IS NULL OR DATEDIFF(CURDATE(),feedback.Create_Date)> :difDay)", nativeQuery = true)
	public Long findByFeedBackCount(Long difDay);

	@Query(value = "SELECT COUNT(*)FROM users_info USER INNER JOIN  quickview_info quickview ON quickview.id=USER.QuickView_Id\r\n"
			+ " INNER JOIN  feedback_info feedback WHERE USER.Status=0 AND USER.Branch_Id=:branchId AND  DATEDIFF(CURDATE(),quickview.Create_Date)>:difDay  \r\n"
			+ " AND (quickview.Feedback_Id IS NULL OR DATEDIFF(CURDATE(),feedback.Create_Date)> :difDay)", nativeQuery = true)
	public Long findByFeedBackCount(Long difDay, Long branchId);
	
	
	
	
	
	
	
	
	@Query(value = "SELECT  YEAR(Create_Date) AS YEAR,MONTH(Create_Date) AS MONTH,COUNT(*) AS TOTAL , SUM(CASE WHEN STATUS =0 THEN 1 ELSE 0 END) AS ACTIVE , SUM(CASE WHEN STATUS =1 THEN 1 ELSE 0 END) AS INACTIVE FROM users_info WHERE QuickView_Id IS NOT NULL GROUP BY YEAR(Create_Date),MONTH(Create_Date)", nativeQuery = true)
	public List<Map> findClientActivity();

	@Query(value = "SELECT  YEAR(Create_Date) AS YEAR,MONTH(Create_Date) AS MONTH, SUM(CASE WHEN STATUS =0 THEN 1 ELSE 0 END) AS ACTIVE , SUM(CASE WHEN STATUS =1 THEN 1 ELSE 0 END) AS INACTIVE FROM users_info WHERE QuickView_Id IS NOT NULL AND Branch_Id=:branchId GROUP BY YEAR(Create_Date),MONTH(Create_Date)", nativeQuery = true)
	public List<Map>  findClientActivityByBranchId(Long branchId);

	@Query(value = "SELECT  YEAR(enquiry.Create_Date) AS YEAR,MONTH(enquiry.Create_Date) AS MONTH, COUNT(id) AS  TOTAL, SUM(CASE WHEN enquiry.STATUS =1 THEN 1 ELSE 0 END) AS NOT_INTERESTED, SUM(CASE WHEN enquiry.STATUS =3 THEN 1 ELSE 0 END) AS CONVERSION\r\n"
			+ "FROM enquiry_info enquiry GROUP BY \r\n"
			+ "YEAR(enquiry.Create_Date),MONTH(enquiry.Create_Date)", nativeQuery = true)
	public List<Map>  findEnquiryActivity();

	@Query(value = "SELECT  YEAR(enquiry.Create_Date) AS ENQUIRY_YEAR,MONTH(enquiry.Create_Date) AS ENQUIRY_MONTH, COUNT(id) AS  ENQUIRY, SUM(CASE WHEN enquiry.STATUS =1 THEN 1 ELSE 0 END) AS NOT_INTERESTED, SUM(CASE WHEN enquiry.STATUS =3 THEN 1 ELSE 0 END) AS CONVERSION\r\n"
			+ "FROM enquiry_info enquiry WHERE Branch_Id=:branchId GROUP BY \r\n"
			+ "YEAR(enquiry.Create_Date),MONTH(enquiry.Create_Date)\r\n" + "", nativeQuery = true)
	public List<Map>  findEnquiryActivityByBranchId(Long branchId);

	@Query(value = "SELECT  YEAR(Create_Date) AS YEAR,MONTH(Create_Date) AS MONTH,\r\n"
			+ " SUM(CASE WHEN Transaction_Type='"+HawkResources.PAYMENT_TRANSACTION_CREDIT+"' THEN amount ELSE 0 END) AS CREDIT, \r\n"
			+ " SUM(CASE WHEN Transaction_Type='"+HawkResources.PAYMENT_TRANSACTION_DEBIT+"' THEN amount ELSE 0 END) AS DEBIT,\r\n"
			+ " SUM(CASE WHEN SOURCE='New Registration' THEN amount ELSE 0 END) AS 'NEWREGISTRATION',\r\n"
			+ "  SUM(CASE WHEN SOURCE='"+HawkResources.PAYMENT_ENTRY_TYPE_PENDINGDUE+"' THEN amount ELSE 0 END) AS 'PENDINGDUE',\r\n"
			+ "    SUM(CASE WHEN SOURCE='"+HawkResources.ENTRY_TYPE_RENEWAL+"' THEN amount ELSE 0 END) AS 'RENEWAL'\r\n"
			+ "   FROM payment_history GROUP BY YEAR(Create_Date),MONTH(Create_Date)", nativeQuery = true)
	public List<Map>  findAccountActivity();

	@Query(value = "SELECT  YEAR(Create_Date) AS YEAR,MONTH(Create_Date) AS MONTH,\\r\\n\"\r\n"
			+ "			+ \" SUM(CASE WHEN Transaction_Type='\"+HawkResources.PAYMENT_TRANSACTION_CREDIT+\"' THEN amount ELSE 0 END) AS CREDIT, \\r\\n\"\r\n"
			+ "			+ \" SUM(CASE WHEN Transaction_Type='\"+HawkResources.PAYMENT_TRANSACTION_DEBIT+\"' THEN amount ELSE 0 END) AS DEBIT,\\r\\n\"\r\n"
			+ "			+ \" SUM(CASE WHEN SOURCE='New Registration' THEN amount  ELSE 0 END) AS 'NEWREGISTRATION',\\r\\n\"\r\n"
			+ "			+ \"  SUM(CASE WHEN SOURCE='\"+HawkResources.PAYMENT_ENTRY_TYPE_PENDINGDUE+\"' THEN amount ELSE 0 END) AS 'PENDINGDUE',\\r\\n\"\r\n"
			+ "			+ \"    SUM(CASE WHEN SOURCE='\"+HawkResources.ENTRY_TYPE_RENEWAL+\"' THEN amount ELSE 0 END) AS 'RENEWAL'\\r\\n\"\r\n"
			+ "			+ \"   FROM payment_history WHERE Branch_Id=:branchId GROUP BY YEAR(Create_Date),MONTH(Create_Date)", nativeQuery = true)
	public List<Map>  findAccountActivityByBranchId(Long branchId);

	
	
	
	
	

}
