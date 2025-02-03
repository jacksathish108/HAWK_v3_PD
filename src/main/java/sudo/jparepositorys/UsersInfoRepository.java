/*
 * 
 */
package sudo.jparepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sudo.user.entities.UsersInfo;

// TODO: Auto-generated Javadoc
/**
 * The Interface Management_Info_Repository.
 */
@Repository
public interface UsersInfoRepository extends JpaRepository<UsersInfo, Long> {

	/**
	 * Find by search.
	 *
	 * @param h_client_id the h client id
	 * @param h_mobile_no the h mobile no
	 * @return the management info
	 */
	@Query(value = "SELECT users.* FROM users_info users WHERE (Email =:user or Mobile_No =:user) and Status=:status", nativeQuery = true)
	public UsersInfo findByUser(String user, int status);

	@Query(value = "SELECT users.* FROM users_info users WHERE (Mobile_No =:user)", nativeQuery = true)
	public UsersInfo findByMobile(String user);

	@Query(value = "SELECT users.* FROM users_info users WHERE (Email =:user) and Status=:status ", nativeQuery = true)
	public UsersInfo findByEmail(String user, int status);

	@Query(value = "SELECT EXISTS(SELECT users.* FROM users_info users WHERE Id=:id or Mobile_No=:mobileNo or Email=:email)", nativeQuery = true) // Status=:status
																																					// and
	public long isExistIdorMobileorEmail(Long id, String mobileNo, String email);

	@Query(value = "(SELECT users.* FROM users_info users WHERE Id=:id or  Mobile_No=:mobileNo or Email=:email)", nativeQuery = true) // Status=:status
																																		// and
	public UsersInfo findByIdorMobileOrEmail(Long id, String mobileNo, String email);

	@Query(value = "SELECT u.* FROM users_info u INNER JOIN  quickview_info q ON q.id=u.QuickView_Id   WHERE u.Client_Id=:cientId", nativeQuery = true)
	public UsersInfo findByClientId(String cientId);

	
	  @Query(value =
	  "SELECT users.* FROM users_info users WHERE Branch_Id=:branchId", nativeQuery
	  = true) public List<UsersInfo> findByBranchUsers(Long branchId);
	@Query(value = "SELECT users.* FROM users_info users WHERE Branch_Id=:branchId and QuickView_Id is not null", nativeQuery = true)
	public List<UsersInfo> findCliendDetailsByBranchId(Long branchId);
	
	
	@Query(value = "SELECT u.* FROM users_info u INNER JOIN  quickview_info q ON q.id=QuickView_Id INNER JOIN payment_history p ON q.Payment_History_Id=p.id   WHERE p.PendingDue>:minVal", nativeQuery = true)
	public List<UsersInfo> findByPendingDue(Long minVal);

	@Query(value = "SELECT u.* FROM users_info u INNER JOIN  quickview_info q ON q.id=QuickView_Id INNER JOIN payment_history p ON q.Payment_History_Id=p.id   WHERE p.PendingDue>:minVal and u.Branch_Id=:branchId", nativeQuery = true)
	public List<UsersInfo> findByPendingDueOnBranch(Long branchId);

//	@Query(value = "SELECT client.* FROM client_info client WHERE (client_quick_view_id is not null ) and userstatuscode=:status ", nativeQuery = true)
//	public List<Client_Info> findByPaidUser(int status);
//	
//	@Query(value = "SELECT client.* FROM client_info client WHERE (client_quick_view_id is null ) and userstatuscode=:status ", nativeQuery = true)
//	public  List<Client_Info> findByGustUser(int status);

	@Query(value = "SELECT users.* FROM users_info users WHERE QuickView_Id is not null", nativeQuery = true)
	public List<UsersInfo> findByClients();

	@Query(value = "SELECT COUNT(*) FROM users_info WHERE QuickView_Id IS NOT NULL AND STATUS=0 ", nativeQuery = true)
	public Long findAllActiveClients();

	@Query(value = "SELECT COUNT(*) FROM users_info WHERE QuickView_Id IS NOT NULL AND STATUS=0 AND Branch_Id=:branchId ", nativeQuery = true)
	public Long findActiveClientsByBranch(Long branchId);

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
	public String findByPendingDueCount(Long minVal,Long branchId);
	@Query(value = "(SELECT count(*) FROM branch_info branchs WHERE status=0)", nativeQuery = true) //Status=:status and
	public Long findByBranches();
}
