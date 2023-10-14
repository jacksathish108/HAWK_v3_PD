package hawk.controllers;
///*
// * 
// */
//package hawk.controller;
//
//import java.lang.reflect.Type;
//import java.security.GeneralSecurityException;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.TreeMap;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import hawk.dao.Hawk_Json_Object;
//import hawk.entities.Account_Management;
//import hawk.entities.Assement_Information;
//import hawk.entities.Assessment_Module;
//import hawk.entities.Attendance_Info;
//import hawk.entities.Calendar_Information;
//import hawk.entities.Client_Friday_Challenge_Info;
//import hawk.entities.Client_Quick_View;
//import hawk.entities.Client_Workout_Info;
//import hawk.entities.Enquiry_Info;
//import hawk.entities.Feedback_Info;
//import hawk.entities.Friday_Challenge_Info;
//import hawk.entities.Hawk_Login;
//import hawk.entities.Management_Info;
//import hawk.entities.Payment_History;
//import hawk.entities.Workout_Module;
//import hawk.util.Account_Management_Info_Repository;
//import hawk.util.Assement_Information_Repository;
//import hawk.util.Assessment_Module_Repository;
//import hawk.util.Attendance_Information_Repository;
//import hawk.util.Calendar_Information_Repository;
//import hawk.util.Client_Friday_Challenge_Info_Repository;
//import hawk.util.Client_Quick_view_Repository;
//import hawk.util.Client_Workout_Info_Repository;
//import hawk.util.Enquiry_Info_Repository;
//import hawk.util.Feedback_Info_Repository;
//import hawk.util.Friday_Challenge_Info_Repository;
//import hawk.util.Hawk_Login_Info_Repository;
//import hawk.util.Management_Info_Repository;
//import hawk.util.Payment_History_Repository;
//import hawk.util.Work_Group_Info_Repository;
//import hawk.util.Workout_Module_Info_Repository;
//
//// TODO: Auto-generated Javadoc
///**
// * The Class HawkRestController.
// */
//@RestController
//@RequestMapping("${ApiVersion}")
//public class HawkRestController {
//	 
// 	/** The logger. */
// 	Logger logger = LoggerFactory.getLogger(HawkRestController.class);
// 	
//
//	/** The account management info repository. */
//	@Autowired
//	Account_Management_Info_Repository account_management_info_repository;
//
//	/** The workout module info repository. */
//	@Autowired
//	Workout_Module_Info_Repository workout_module_info_Repository;
//
//	/** The work group info repository. */
//	@Autowired
//	Work_Group_Info_Repository work_Group_Info_Repository;
//
//	/** The assement information repository. */
//	@Autowired
//	Assement_Information_Repository assement_Information_Repository;
//
//	/** The assessment module repository. */
//	@Autowired
//	Assessment_Module_Repository assessment_Module_Repository;
//
//	/** The management info repository. */
//	@Autowired
//	Management_Info_Repository management_info_repository;
//	
//	/** The friday challenge info repository. */
//	@Autowired
//	Friday_Challenge_Info_Repository friday_Challenge_Info_Repository;
//	
//	/** The login repository. */
//	@Autowired
//	Hawk_Login_Info_Repository login_repository;
//	
//	/** The attendance information repository. */
//	@Autowired
//	Attendance_Information_Repository attendance_Information_Repository;
//
//	/** The client workout info repository. */
//	@Autowired
//	Client_Workout_Info_Repository client_Workout_Info_Repository;
//
//	/** The client friday challenge info repository. */
//	@Autowired
//	Client_Friday_Challenge_Info_Repository client_Friday_Challenge_Info_Repository;
//
//	/** The client quick view repository. */
//	@Autowired
//	Client_Quick_view_Repository client_quick_view_repository;
//	
//	/** The payment history repository. */
//	@Autowired
//	Payment_History_Repository payment_History_Repository;
//	
//	/** The calendar information repository. */
//	@Autowired
//	Calendar_Information_Repository calendar_Information_Repository;
//	
//	/** The enquiry info repository. */
//	@Autowired
//	Enquiry_Info_Repository enquiry_Info_Repository;
//	
//	/** The feedback info repository. */
//	@Autowired
//	Feedback_Info_Repository feedback_Info_Repository;
//	
//	/** The now. */
//	Timestamp now;
//	
//	/** The active. */
//	String active="Active";
//	
//	/** The super user. */
//	int superUser=111;
//
//	/** The hawk login. */
//	static Hawk_Login hawk_Login;
//	
//	/**
//	 * Gets the management info active clients.
//	 *
//	 * @return the management info active clients
//	 */
//	@GetMapping("/get_client_details")
//	public List<Management_Info> get_managementInfoActiveClients() {
//		
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				System.out.println(hawk_json_object);	
//		return management_info_repository.findAll();
//	}
//
//
//	/**
//	 * New client.
//	 *
//	 * @param request the request
//	 * @param management_Info the management info
//	 * @param payment_History the payment history
//	 * @return the hawk json object
//	 */
//	@PostMapping("/new_client")
//	public Hawk_Json_Object new_client(HttpServletRequest request,Management_Info management_Info, Payment_History payment_History) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//				try {
//					hawk_json_object=sessionCheck(hawk_json_object);
//					if(hawk_json_object.getSessionStatus()==false)
//						return hawk_json_object;			
//
//			if (management_Info.getClient_Name() != null && management_Info.getMobile_No() != null
//				 && management_Info.getMobile_No() != null) {
//				Management_Info result = management_info_repository.find_by_search( management_Info.getClient_id(),management_Info.getMobile_No());
//				if (result == null || result.getClient_id() != null && result.getMobile_No() != null) {
//
//					Timestamp new_timestamp = new Timestamp(System.currentTimeMillis());
//					Date date=new Date();
//					Calendar cal = Calendar.getInstance();
//					Calendar now = Calendar.getInstance();
//					
//					if(management_Info.getCus_date()!=null)
//					{
//						 date=new SimpleDateFormat("dd/MM/yyyy").parse(management_Info.getCus_date());
//						new_timestamp = new Timestamp(date.getTime());
//						now.setTime(date);
//					}
//					cal.setTime(date);
//					cal.add(Calendar.MONTH, Integer.parseInt(payment_History.getPackage_Duration().split(" ")[0]));
//					if(payment_History.getExtension()!=null&&!payment_History.getExtension().isEmpty())
//					cal.add(Calendar.DATE, Integer.parseInt(payment_History.getExtension()));
//					java.util.Date expirationDate = cal.getTime();
//					 int client_id  = management_info_repository.find_by_desc(); 
//					management_Info.setClient_id("HF" +String.format("%ty", now) +String.format("%tm", now)+String.format("%02d",(client_id+1)));
//					Client_Quick_View client_Quick_View = new Client_Quick_View();
//					client_Quick_View.setClient_id(management_Info.getClient_id());
//					management_Info.setCreate_Date(new_timestamp);
//					management_Info.setcreatedBy(hawk_Login.getUser_id());
//					management_Info.setRenewal_Date(new_timestamp);
//					client_Quick_View.setFeedback("");
//					client_Quick_View.setGoogle_review("");
//					client_Quick_View.setWorkout_Chart("");
//					client_Quick_View.setClient_Status(active);
//					client_Quick_View.setStart_Date(management_Info.getCreate_Date());
//					payment_History.setRenewal_Date(new Timestamp(expirationDate.getTime()));
//					client_Quick_View.setEnd_Date(payment_History.getRenewal_Date());
//					client_Quick_View.setDuration((String.valueOf(ChronoUnit.DAYS.between(client_Quick_View.getStart_Date().toLocalDateTime(),client_Quick_View.getEnd_Date().toLocalDateTime()))));
//					management_Info.setClient_Quick_View(client_Quick_View);
//					management_Info.setUser_pwd(management_Info.getClient_id());
//					long due_amount = (payment_History.getTraiff_Amount()- payment_History.getDiscount());
//					due_amount = due_amount - (payment_History.getPaid_Amount());
//					payment_History.setDue_Amount(due_amount);
//					payment_History.setDuePay(payment_History.getPaid_Amount());
//					payment_History.setSource_Category("NewClient");
//					payment_History.setClient_id(management_Info.getClient_id());
//					payment_History.setCreate_Date(management_Info.getCreate_Date());
//					management_Info.setPayment_History(payment_History);
//					management_info_repository.saveAndFlush(management_Info);
//					Enquiry_Info enquiry_Info = enquiry_Info_Repository.find_by_mobile(management_Info.getMobile_No());
//					if(enquiry_Info!=null)
//					{
//						enquiry_Info.setStatus(2);
//						hawk_json_object.setData(enquiry_Info_Repository.save(enquiry_Info));
//					}
//					Account_Management acc_management_Info=new Account_Management();
//					acc_management_Info.setCreate_date(new Timestamp(System.currentTimeMillis()));
//					acc_management_Info.setMode("Revenue");
//					acc_management_Info.setPayment_Type(payment_History.getPayment_Type());
//					acc_management_Info.setCategory(payment_History.getSource_Category());
//					acc_management_Info.setAmount(payment_History.getDuePay());
//					acc_management_Info.setRemarks(management_Info.getClient_id() +" - "+payment_History.getCategory());
//					acc_management_Info.setCreatedBy(hawk_Login.getUser_id());
//					account_management_info_repository.save(acc_management_Info);
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				}
//			}
//
//		} catch (Exception e) {
//			hawk_json_object.setStatus("0");
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Update client.
//	 *
//	 * @param management_Info the management info
//	 * @param srarch_object the srarch object
//	 * @return the hawk json object
//	 */
//	@PostMapping("/update_client")
//	public Hawk_Json_Object update_client(Management_Info management_Info, String srarch_object) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		logger.info("Management_Info   : " + management_Info.toString());
//			if (management_Info.getClient_Name() != null && management_Info.getMobile_No() != null
//				&& management_Info.getMobile_No() != null) {
//				Management_Info result = management_info_repository.find_by_search(management_Info.getClient_id(),management_Info.getMobile_No());
//				if (result != null) {
//
//					result.setBody_Type(management_Info.getBody_Type());
//					result.setClient_Name(management_Info.getClient_Name());
//					result.setMobile_No(management_Info.getMobile_No());
//					result.setAddress(management_Info.getAddress());
//					result.setDOB(management_Info.getDOB());
//					result.setEmail(management_Info.getEmail());
//					result.setFacebook_Id(management_Info.getFacebook_Id());
//					result.setInstagram_Id(management_Info.getInstagram_Id());
//					result.setWeight(management_Info.getWeight());
//					result.setPARQ(management_Info.getPARQ());
//					result.setRemarks(management_Info.getRemarks());
//					management_Info.setCreate_Date(new Timestamp(System.currentTimeMillis()));
//					result.setcreatedBy(hawk_Login.getUser_id());
//					management_info_repository.save(result);
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				}
//			}
//			else
//			{
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			hawk_json_object.setStatus("0");
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Update general information.
//	 *
//	 * @param management_Info the management info
//	 * @return the hawk json object
//	 */
//	@PostMapping("/update_general_information")
//	public Hawk_Json_Object update_general_information(Management_Info management_Info) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			if (management_Info.getClient_id() != null &&management_Info.getClient_Name() != null && management_Info.getMobile_No() != null&& management_Info.getDOB() != null) 
//					{
//				Management_Info result = management_info_repository.find_by_client_id(management_Info.getClient_id());
//				if (result != null) {
//					result.setClient_Name(management_Info.getClient_Name());
//					result.setMobile_No(management_Info.getMobile_No());
//					result.setAddress(management_Info.getAddress());
//					result.setDOB(management_Info.getDOB());
//					result.setGender(management_Info.getGender());
//					result.setEmail(management_Info.getEmail());
//					result.setFacebook_Id(management_Info.getFacebook_Id());
//					result.setInstagram_Id(management_Info.getInstagram_Id());
//					result.setUpdate_Date(new Timestamp(System.currentTimeMillis()));
//					result.setUpdatedBy(management_Info.getClient_id());
//					management_info_repository.save(result);
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				}
//			}
//			else
//			{
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			hawk_json_object.setStatus("0");
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Client renewal.
//	 *
//	 * @param payment_History the payment history
//	 * @param management_info_inf the management info inf
//	 * @return the hawk json object
//	 */
//	@PostMapping("/client_Renewal")
//	public Hawk_Json_Object client_Renewal(Payment_History payment_History,Management_Info management_info_inf) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			if(payment_History.getClient_id()!=null&&payment_History.getPackage_Duration()!=null&&payment_History.getTarget()!=null
//					&&payment_History.getTraiff_Amount()!=null&&payment_History.getTraiff_Amount()!=null)
//			{				
//					Management_Info management_info = management_info_repository.find_by_client_id(payment_History.getClient_id());
//					Timestamp new_timestamp = new Timestamp(System.currentTimeMillis());
//					Date date=new Date();
//					Calendar cal = Calendar.getInstance();
//					Calendar now = Calendar.getInstance();
//					if(management_info_inf.getCus_date()!=null)
//					{
//						 date=new SimpleDateFormat("dd/MM/yyyy").parse(management_info_inf.getCus_date());
//						new_timestamp = new Timestamp(date.getTime());
//						now.setTime(date);
//					}
//					else {
//						date=(management_info.getPayment_History().getRenewal_Date());
//					}
//						new_timestamp = new Timestamp(date.getTime());
//						now.setTime(date);
//						cal.setTime(date);
//						cal.add(Calendar.MONTH, Integer.parseInt(payment_History.getPackage_Duration().split(" ")[0]));
//						if(payment_History.getExtension()!=null&&!payment_History.getExtension().isEmpty())
//						cal.add(Calendar.DATE, Integer.parseInt(payment_History.getExtension()));
//						java.util.Date expirationDate = cal.getTime();
//					payment_History.setRenewal_Date(new Timestamp(expirationDate.getTime()));
//					payment_History.setCreate_Date(new_timestamp);
//					payment_History.setSource_Category("Renewal");
//					/////
//					long due_amount = (payment_History.getTraiff_Amount()- payment_History.getDiscount());
//					due_amount = due_amount - (payment_History.getPaid_Amount());
//					payment_History.setDue_Amount(due_amount);
//					payment_History.setDuePay(payment_History.getPaid_Amount());  
//					  management_info.setPayment_History(payment_History_Repository.saveAndFlush(payment_History));
//				/////
//					Client_Quick_View client_Quick_View = new Client_Quick_View();
//					client_Quick_View.setClient_id(payment_History.getClient_id());
//					client_Quick_View.setFeedback(management_info.getClient_Quick_View().getFeedback());
//					client_Quick_View.setGoogle_review(management_info.getClient_Quick_View().getGoogle_review());
//					client_Quick_View.setWorkout_Chart(management_info.getClient_Quick_View().getWorkout_Chart());
//					client_Quick_View.setWarm_ups(management_info.getClient_Quick_View().getWarm_ups());
//					client_Quick_View.setStretching(management_info.getClient_Quick_View().getStretching());
//					client_Quick_View.setClient_Status(active);
//					client_Quick_View.setStart_Date(payment_History.getCreate_Date());
//					
//					client_Quick_View.setEnd_Date(payment_History.getRenewal_Date());
//					management_info.setRenewal_Date(new_timestamp);
//					client_Quick_View.setDuration((String.valueOf(ChronoUnit.DAYS.between(client_Quick_View.getStart_Date().toLocalDateTime(),client_Quick_View.getEnd_Date().toLocalDateTime()))));
//					management_info.setClient_Quick_View(client_quick_view_repository.save(client_Quick_View));
//					management_info.setPayment_History(payment_History_Repository.save(payment_History));
//					management_info.setcreatedBy(hawk_Login.getUser_id());
//					management_info_repository.save(management_info);
//					Account_Management acc_management_Info=new Account_Management();
//					acc_management_Info.setCreate_date(new Timestamp(System.currentTimeMillis()));
//					acc_management_Info.setMode("Revenue");
//					acc_management_Info.setPayment_Type(payment_History.getPayment_Type());
//					acc_management_Info.setCategory(payment_History.getSource_Category());
//					acc_management_Info.setAmount(payment_History.getDuePay());
//					acc_management_Info.setRemarks(management_info.getClient_id() +" - "+payment_History.getCategory());
//					acc_management_Info.setCreatedBy(hawk_Login.getUser_id());
//					account_management_info_repository.save(acc_management_Info);
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				}
//			else
//			{
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			hawk_json_object.setStatus("0");
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//	
//	
//	
//	
//	
//	/**
//	 * Update pending due.
//	 *
//	 * @param payment_History the payment history
//	 * @return the hawk json object
//	 */
//	@PostMapping("/update_pending_due")
//	public Hawk_Json_Object update_pending_due(Payment_History payment_History) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		if(payment_History.getClient_id()!=null)
//			{
//				Management_Info management_info = management_info_repository.find_by_client_id(payment_History.getClient_id());
//			logger.info("payment_History    ::: "+payment_History);
//			logger.info("management_info    ::: "+management_info);
//			
//				payment_History.setDue_Amount(management_info.getPayment_History().getDue_Amount()-payment_History.getDuePay());
//				payment_History.setDuePay(payment_History.getDuePay());
//				payment_History.setPayment_Type(payment_History.getPayment_Type());
//				payment_History.setDue_Date(payment_History.getDue_Date());
//				payment_History.setCreate_Date(new Timestamp(System.currentTimeMillis()));
//				payment_History.setSource_Category("pending_due");
//				payment_History.setCategory(management_info.getPayment_History().getCategory());
//				payment_History.setDiscount(management_info.getPayment_History().getDiscount());
//				payment_History.setPackage_Duration(management_info.getPayment_History().getPackage_Duration());
//				payment_History.setPaid_Amount(management_info.getPayment_History().getPaid_Amount()+payment_History.getDuePay());
//				payment_History.setRenewal_Date(management_info.getPayment_History().getRenewal_Date());
//				payment_History.setTarget(management_info.getPayment_History().getTarget());
//				payment_History.setTraiff_Amount(management_info.getPayment_History().getTraiff_Amount());
//				 logger.info("management_info 0 : "+management_info);	  
//				  management_info.setPayment_History(payment_History_Repository.saveAndFlush(payment_History));
//				  logger.info("management_info 1 : "+management_info);
//					management_info.setcreatedBy(hawk_Login.getUser_id());
//				  management_info_repository.save(management_info);
//				  Account_Management acc_management_Info=new Account_Management();
//					acc_management_Info.setCreate_date(payment_History.getCreate_Date());
//					acc_management_Info.setMode("Revenue");
//					acc_management_Info.setPayment_Type(payment_History.getPayment_Type());
//					acc_management_Info.setCategory(payment_History.getSource_Category());
//					acc_management_Info.setAmount(payment_History.getDuePay());
//					acc_management_Info.setRemarks(management_info.getClient_id() +" - "+payment_History.getCategory());
//					account_management_info_repository.save(acc_management_Info);
//				  hawk_json_object.setStatus("1");
//				  return hawk_json_object;
//				 }
//			else
//			{
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			hawk_json_object.setStatus("0");
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Enquiry follow.
//	 *
//	 * @param enquiry_Info the enquiry info
//	 * @return the hawk json object
//	 */
//	@PostMapping("/enquiry_follow")
//	public Hawk_Json_Object enquiry_follow(Enquiry_Info enquiry_Info) {
//		 now = new Timestamp(System.currentTimeMillis());
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//		logger.info("Management_Info  enquiry_follow : " + enquiry_Info.toString());
//			if (enquiry_Info.getClient_Name() != null && enquiry_Info.getMobile_No() != null) {
//				
//
//				if(enquiry_Info.getId()==null)
//				{				
//					enquiry_Info.setCreate_Date(now);
//					enquiry_Info.setStatus(1);
//					hawk_json_object.setData(enquiry_Info_Repository.save(enquiry_Info));
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				}
//				else
//				{
//					Optional<Enquiry_Info> result =enquiry_Info_Repository.findById(enquiry_Info.getId());
//					if(result!=null)
//					{
//					enquiry_Info.setCreate_Date(((Enquiry_Info)result.get()).getCreate_Date());
//					enquiry_Info.setUpdate_Date(now);
//					enquiry_Info.setStatus(1);
//					hawk_json_object.setData(enquiry_Info_Repository.save(enquiry_Info));
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				
//				}
//					else
//					{
//						hawk_json_object.setStatus("0");
//						return hawk_json_object;
//					}
//				}
//				
//			}
//			else
//			{
//				hawk_json_object.setStatus("0");
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * Enquiry delete.
//	 *
//	 * @param id the id
//	 * @return the hawk json object
//	 */
//	@GetMapping("/enquiry_delete/{id}")
//	public Hawk_Json_Object enquiry_delete(@PathVariable(value = "id") String id) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			logger.info(id);
//				if (id != null) {
//				 Enquiry_Info enquiry_Info = enquiry_Info_Repository.find_by_mobile(id);
//				 if(enquiry_Info!=null) 
//				 {
//					 enquiry_Info.setStatus(0);
//					 enquiry_Info_Repository.save(enquiry_Info); 
//					 hawk_json_object.setStatus("1");
//				 }else
//				 {
//					 hawk_json_object.setStatus("0");
//				 }
//			} else {
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Login.
//	 *
//	 * @param request the request
//	 * @param hawkLogin the hawk login
//	 * @return the hawk json object
//	 */
//	@PostMapping("/login")
//	public Hawk_Json_Object login(HttpServletRequest request, Hawk_Login hawkLogin) {
//		try {
//			System.out.println(" called login");
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			hawk_Login = login_repository.login(hawkLogin.getUser_name(), hawkLogin.getUser_pwd());
//			if (hawk_Login != null) {
//				hawk_json_object.setStatus("1");
//				hawk_json_object.setView("index");
//				hawk_json_object.setData(hawk_Login);
//				return hawk_json_object;
//			} else {
//				request.getSession().setAttribute("hawk_Login", "null");
//				hawk_json_object.setView("");
//				hawk_json_object.setStatus("0");
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	/**
//	 * Login.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
////	@PostMapping("/logout")
////	public Hawk_Json_Object login(HttpServletRequest request) {
////		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
////		try {
////			hawk_Login =null; 
////		hawk_json_object=sessionCheck(hawk_json_object);		
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		return hawk_json_object;
////
////	}
//
//
//	/**
//	 * Login new.
//	 *
//	 * @param hawk_Login the hawk login
//	 */
//	@PostMapping("/login_save")
//	public void login_new(Hawk_Login hawk_Login) {
//		try {
//			logger.info("hawk_Login   : " + hawk_Login.toString());
//			login_repository.save(hawk_Login);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * Dasbord.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/dasbord")
//	public Hawk_Json_Object dasbord(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//	
//			HashMap<String, Object> visual_data = new HashMap<String, Object>();
//			if(hawk_Login.getUser_group()==superUser)
//				visual_data.put("user_group", 1);
//			else
//				visual_data.put("user_group", 0);
//			visual_data.put("conversion_count", management_info_repository.find_by_conversion());
//			int feedback_count = 0,google_reviw_count=0,attendance_count=0;
//			List<Management_Info> feedback_List =feedbackFollowUpList();
//			List<Management_Info> google_reviw_List =googleReviewkList();
//			if(feedback_List!=null)
//				 feedback_count=feedback_List.size();
//			 if(google_reviw_List!=null)
//				 google_reviw_count=google_reviw_List.size();
//			 
//			visual_data.put("feedback_count",feedback_count );
//			
//			visual_data.put("google_reviw_count", google_reviw_count);
//		List<Enquiry_Info> enquiryList = enquiry_Info_Repository.find_by_follow();
//			visual_data.put("enquiry_count",enquiryList.size());
//				int renewal_list_count=0,insanity_assesment_count=0,Pending_due_amount=0,pending_due_count=0,workout_chart_followup_count=0;
//			for(Management_Info management_Info:managementInfoActiveClients())
//			{
//					if(getDifferenceDays(new Date(management_Info.getPayment_History().getRenewal_Date().getTime()),new Date())>=0)
//
//					renewal_list_count++;
//				if(management_Info.getPayment_History()!=null&&management_Info.getPayment_History().getDue_Amount()!=null&&!management_Info.getPayment_History().getDue_Amount().equals("null")&&management_Info.getPayment_History().getDue_Amount()>0)
//				{
//					Pending_due_amount+=management_Info.getPayment_History().getDue_Amount();
//					pending_due_count++;
//				}
//			}
//
//			for(Management_Info management_Info:managementInfoActiveClients())
//			{ 
//				if(management_Info.getClient_Quick_View().getWarm_ups()==null||management_Info.getClient_Quick_View().getWarm_ups().isEmpty()||management_Info.getClient_Quick_View().getStretching()==null||management_Info.getClient_Quick_View().getStretching().isEmpty()||management_Info.getClient_Quick_View().getWorkout_Chart()==null||management_Info.getClient_Quick_View().getWorkout_Chart().isEmpty())
//					workout_chart_followup_count++;
//			}
//			String pattern = "yyyy-MM-dd";
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//			List<Attendance_Info> attendance_Info = attendance_Information_Repository.attendance_by_today(simpleDateFormat.format(new Date()));
//			if(attendance_Info!=null)			
//				attendance_count=attendance_Info.size();
//			Hawk_Json_Object insanity_assesment = insanity_assesment_report(request);
//			if(insanity_assesment!=null&&insanity_assesment.getData()!=null)
//			insanity_assesment_count=insanity_assesment.getCount();
//			visual_data.put("client_count",managementInfoActiveClients().size());
//			visual_data.put("workout_chart_followup_count",workout_chart_followup_count );
//			visual_data.put("calendar_event", calendar_Information_Repository.findAll());
//			visual_data.put("pending_due_count", pending_due_count);
//			visual_data.put("pending_due_amount",Pending_due_amount);
//			visual_data.put("insanity_assesment_count",insanity_assesment_count);
//			visual_data.put("renewal_list_count",renewal_list_count);
//			visual_data.put("client_details", management_info_repository.findAll());
//			visual_data.put("enquiry_details",enquiry_Info_Repository.findAll());
//			visual_data.put("attendance_count",attendance_count);
//			hawk_json_object.setVisual_object(visual_data);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	/**
//	 * Enquiry report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/enquiry_report")
//	public Hawk_Json_Object enquiry_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			logger.info("enquiry_report");
//			hawk_json_object.setData(enquiry_Info_Repository.find_by_enquiry());
//			logger.info("hawk_json_object  :: "+hawk_json_object.getData());
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//
//	/**
//	 * Renewal report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/renewal_report")
//	public Hawk_Json_Object Renewal_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			
//			List<Management_Info> management_infoListSort=new ArrayList<Management_Info>();
//			for(Management_Info management_Info:managementInfoActiveClients())
//			{				
//			if(management_Info!=null&&getDifferenceDays(new Date(management_Info.getPayment_History().getRenewal_Date().getTime()),new Date())>=0)
//				management_infoListSort.add(management_Info);
//			}
//			hawk_json_object.setData(management_infoListSort);
//			logger.info("renewal_report  "+hawk_json_object.getData());
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	/**
//	 * Pending due report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/pending_due_report")
//	public Hawk_Json_Object pending_due_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//	hawk_json_object.setData(managementInfoActiveClients());
//
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	
//	
//	/**
//	 * Insanity assesment report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/insanity_assesment_report")
//	public Hawk_Json_Object insanity_assesment_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			List<Management_Info> management_Info_List =managementInfoActiveClients().stream().filter(client ->client.getPayment_History().getCategory().matches("Insanity|Personal")).collect(Collectors.toList());
//			List<Management_Info> management_InfoResult=new ArrayList<Management_Info>();
//		if(management_Info_List!=null)
//			for(Management_Info management_Info:management_Info_List)
//			{
//				   List<Assessment_Module> assessment_Module = assessment_Module_Repository.find_Assement_Information_client_id_desc(management_Info.getClient_id(),management_Info.getRenewal_Date().toString());
//           if(assessment_Module!=null&&!assessment_Module.isEmpty()) {
//    	 			{
//    			    long entroll_days_count= TimeUnit.DAYS.convert((new Date().getTime()-management_Info.getRenewal_Date().getTime()), TimeUnit.MILLISECONDS);
//    			    if(entroll_days_count>=0&&entroll_days_count<30 &&assessment_Module.size()<1)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=30&&entroll_days_count<60 &&assessment_Module.size()<2)
//		    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=60&&entroll_days_count<90 &&assessment_Module.size()<3)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=90&&entroll_days_count<120 &&assessment_Module.size()<4)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=120&&entroll_days_count<150 &&assessment_Module.size()<5)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=150&&entroll_days_count<180 &&assessment_Module.size()<6)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=180&&entroll_days_count<210 &&assessment_Module.size()<7)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=210&&entroll_days_count<240 &&assessment_Module.size()<8)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=240&&entroll_days_count<270 &&assessment_Module.size()<9)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=270&&entroll_days_count<300 &&assessment_Module.size()<10)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=300&&entroll_days_count<330 &&assessment_Module.size()<11)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=330&&entroll_days_count<360 &&assessment_Module.size()<12)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=360&&entroll_days_count<390 &&assessment_Module.size()<14)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=390&&entroll_days_count<420 &&assessment_Module.size()<15)
//		    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=420&&entroll_days_count<450 &&assessment_Module.size()<16)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=420&&entroll_days_count<480 &&assessment_Module.size()<17)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=480&&entroll_days_count<510 &&assessment_Module.size()<18)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=510&&entroll_days_count<540 &&assessment_Module.size()<19)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=540&&entroll_days_count<570 &&assessment_Module.size()<20)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=570&&entroll_days_count<600 &&assessment_Module.size()<21)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=600&&entroll_days_count<630 &&assessment_Module.size()<22)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=630&&entroll_days_count<660 &&assessment_Module.size()<23)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=660&&entroll_days_count<690 &&assessment_Module.size()<24)
//			    	management_InfoResult.add(management_Info);
//			    else if(entroll_days_count>=690&&entroll_days_count<720 &&assessment_Module.size()<25)
//			    	management_InfoResult.add(management_Info);
//    			    
//    			    String dates= (new SimpleDateFormat("dd/MM/yyyy")).format(Timestamp.valueOf(new Timestamp(management_Info.getRenewal_Date().getTime()).toLocalDateTime().plusDays(assessment_Module.size()*30))); 
//    				    			    // dates+=" - "; 
//    			    //dates+= (new SimpleDateFormat("dd/MM/yyyy")).format(Timestamp.valueOf(new Timestamp(management_Info.getRenewal_Date().getTime()).toLocalDateTime().plusDays((assessment_Module.size()*30)+30))); 
//    			    management_Info.setRemarks(dates);
//    			    }
//}
//      else
//      {
//      management_Info.setRemarks((new SimpleDateFormat("dd/MM/yyyy")).format((management_Info.getRenewal_Date().getTime())));
//    	  management_InfoResult.add(management_Info);
//      }
//			}
//			hawk_json_object.setData(management_InfoResult);
//			hawk_json_object.setCount(management_InfoResult.size());
//
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//
//	/**
//	 * Workout chart followup report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/workout_chart_followup_report")
//	public Hawk_Json_Object workout_chart_followup_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			Map<String, Object> result = new TreeMap();
//			result.put("client_quick_view",managementInfoActiveClients().stream().filter(client->
//					client.getClient_Quick_View().getWarm_ups()==null||
//					client.getClient_Quick_View().getWarm_ups().isEmpty()||
//					client.getClient_Quick_View().getStretching()==null||
//					client.getClient_Quick_View().getStretching().isEmpty()|| 
//					client.getClient_Quick_View().getWorkout_Chart()==null||
//					client.getClient_Quick_View().getWorkout_Chart().isEmpty()
//					).collect(Collectors.toList()));	
//
//			
//			
//			result.put("wod", workout_module_info_Repository.find_Assement_Information_by_client_grouping());
//			logger.info("management_Info_List  "+result);
//			hawk_json_object.setData(result);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	
//	
//	
//	
//	/**
//	 * Conversion rate report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/conversion_rate_report")
//	public Hawk_Json_Object conversion_rate_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			hawk_json_object.setData(managementInfoActiveClients());
//
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	
//	
//
//	/**
//	 * Search.
//	 *
//	 * @param client_details the client details
//	 * @return the hawk json object
//	 */
//	@PostMapping("/search")
//	public Hawk_Json_Object search(Management_Info client_details) {
//
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			logger.info("client_details  :: " + client_details);
//			if (client_details.getClient_id() != null) {
//				client_details = management_info_repository.find_by_search(client_details.getClient_id(), client_details.getClient_id());
//				hawk_json_object.setData(client_details);
//				logger.info("client_details000  :: " + client_details);
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	/**
//	 * Removes the employee details.
//	 *
//	 * @param client_id the client id
//	 * @return the hawk json object
//	 */
//	@GetMapping("/client_delete/{id}")
//	public Hawk_Json_Object remove_employee_details(@PathVariable(value = "id") String client_id) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			logger.info(client_id);
//			if (client_id != null) {
//				Management_Info management_inf = management_info_repository.find_by_client_id(client_id);
//				management_info_repository.deleteById(management_inf.getId());
//				Client_Quick_View clint_quick_view = new Client_Quick_View();
//				clint_quick_view.setClient_id(client_id);
//				client_quick_view_repository.delete(clint_quick_view);
//				client_Friday_Challenge_Info_Repository.delete_by_client_id(management_inf.getClient_id());
//				client_Workout_Info_Repository.delete_by_client_id(management_inf.getClient_id());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Account management entry.
//	 *
//	 * @param acc_management_Info the acc management info
//	 * @return the hawk json object
//	 */
//	@PostMapping("/account_management_entry")
//	public Hawk_Json_Object account_management_entry(Account_Management acc_management_Info) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		 now = new Timestamp(System.currentTimeMillis());
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//
//			if (acc_management_Info.getCategory() != null && acc_management_Info.getMode() != null
//					&& acc_management_Info.getAmount() != null) {
//				acc_management_Info.setCreate_date(now);
//				acc_management_Info.setCreatedBy(hawk_Login.getUser_id());
//				account_management_info_repository.save(acc_management_Info);
//				hawk_json_object.setStatus("1");
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Account management list.
//	 *
//	 * @return the hawk json object
//	 */
//	@PostMapping("/account_management_list")
//	public Hawk_Json_Object account_management_list() {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			List<Account_Management> account_ManagementList = account_management_info_repository.findAll();
//			
//			for(Account_Management account_Management:account_ManagementList)
//			{
//				try {
//				String[] remarks = account_Management.getRemarks().split("-");
//				if(remarks.length>1)
//				{
//					Management_Info management_Info = management_info_repository.find_by_client_id(remarks[0]);
//					account_Management.setRemarks(management_Info.getClient_Name()+" - "+remarks[1]);
//				}
//				}
//				catch (Exception e) {
//					logger.info("Invalid account_Management row...");
//				}
//				
//			}
//			//acc_management_Info.setRemarks(management_Info.getClient_id()
//			
//			
//			
//			logger.info("account_management_list :: " + account_ManagementList);
//			hawk_json_object.setData(account_ManagementList);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Friday challenge entry.
//	 *
//	 * @param friday_Challenge_Info the friday challenge info
//	 * @return the hawk json object
//	 */
//	@PostMapping("/friday_challenge_entry")
//	public Hawk_Json_Object friday_challenge_entry(Friday_Challenge_Info friday_Challenge_Info) {
//			 now = new Timestamp(System.currentTimeMillis());
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//
//			logger.info("friday_Challenge_Info  :: " + friday_Challenge_Info.toString());
//			if (friday_Challenge_Info.getWOD() != null && friday_Challenge_Info.getTarget_Limit() != null
//					&& friday_Challenge_Info.getEvent_date() != null) {
//				friday_Challenge_Info.setCreate_date(now);
//				Date date = (new SimpleDateFormat("dd/MM/yyyy").parse(friday_Challenge_Info.getEvent_date()));
//				Timestamp ew_timestamp = new Timestamp(date.getTime());
//				friday_Challenge_Info.setEvent_date(ew_timestamp.toString());
//				Friday_Challenge_Info res = friday_Challenge_Info_Repository.save(friday_Challenge_Info);
//				Calendar_Information calendar_Information=new Calendar_Information();
//				calendar_Information.setCreate_Date(new Timestamp(System.currentTimeMillis()));
//				calendar_Information.setEvent_Date(friday_Challenge_Info.getEvent_date());
//				calendar_Information.setTitle("Friday Challenge");
//				calendar_Information.setMessage("All the best...");
//				calendar_Information_Repository.save(calendar_Information);
//				
//				hawk_json_object.setData(res);
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Friday challenge list.
//	 *
//	 * @return the hawk json object
//	 */
//	@PostMapping("/friday_challenge_list")
//	public Hawk_Json_Object friday_challenge_list() {
//		TreeMap<String, Object> result = new TreeMap<String, Object>();
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//
//			result.put("friday_challenge_list", friday_Challenge_Info_Repository.findAll());
//			result.put("wod", workout_module_info_Repository.find_Assement_Information_by_client_grouping("WOD"));
//			hawk_json_object.setData(result);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Friday challenge event date.
//	 *
//	 * @param Client_Friday_Challenge_Info the client friday challenge info
//	 * @return the hawk json object
//	 */
//	@PostMapping("/friday_challenge_event")
//	public Hawk_Json_Object friday_challenge_event_date(Client_Friday_Challenge_Info Client_Friday_Challenge_Info) {
//
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		logger.info(Client_Friday_Challenge_Info.toString());
//			if (Client_Friday_Challenge_Info.getEvent_date() != null
//					&& Client_Friday_Challenge_Info.getReps_count() != null) {
//				
//				Date date = (new SimpleDateFormat("dd/MM/yyyy").parse(Client_Friday_Challenge_Info.getEvent_date()));
//				Timestamp ew_timestamp = new Timestamp(date.getTime());
//				Client_Friday_Challenge_Info.setEvent_date(ew_timestamp.toString());				
//				if (Client_Friday_Challenge_Info.getReps_count().toLowerCase().contains("time"))
//					hawk_json_object.setData(client_Friday_Challenge_Info_Repository
//							.find_by_date_asc(Client_Friday_Challenge_Info.getEvent_date()));
//				else
//					hawk_json_object.setData(client_Friday_Challenge_Info_Repository
//							.find_by_date_desc(Client_Friday_Challenge_Info.getEvent_date()));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Assement module entry.
//	 *
//	 * @param assessment_Module the assessment module
//	 * @return the hawk json object
//	 */
//	@PostMapping("/assement_module_entry")
//	public Hawk_Json_Object assement_module_entry(Assessment_Module assessment_Module) {
//			 now = new Timestamp(System.currentTimeMillis());
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//			if (assessment_Module.getClient_id() != null && assessment_Module.getTest() != null
//					&& assessment_Module.getMeasurement() != null && assessment_Module.getRemarks() != null) {
//				assessment_Module.setCreate_date(now);
//				hawk_json_object.setData(assessment_Module_Repository.save(assessment_Module));
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Assement module list.
//	 *
//	 * @return the hawk json object
//	 */
//	@PostMapping("/assement_module_list")
//	public Hawk_Json_Object assement_module_list() {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			List<Assessment_Module> res = assessment_Module_Repository.findAll();
//			Map<String, Object> result = new TreeMap();
//			result.put("assessment_Module", res);
//			hawk_json_object.setSuporting_data(assement_Information_Repository.find_Assement_Information_all());
//			hawk_json_object.setData(res);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Workout module entry.
//	 *
//	 * @param assessment_Module the assessment module
//	 * @return the hawk json object
//	 * @throws GeneralSecurityException the general security exception
//	 */
//	@PostMapping("/workout_module_entry")
//	public Hawk_Json_Object workout_module_entry(Hawk_Json_Object assessment_Module) throws GeneralSecurityException {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//
//			// logger.info("workout_module_entry :: " +
//			// assessment_Module.getObject_list().size() +" :: "+assessment_Module);
//			logger.info("workout_module_entry   ::   " + assessment_Module.getObject_list());
//
//			Gson gson = new Gson();
//			Type userListType = new TypeToken<ArrayList<Workout_Module>>() {
//			}.getType();
//			ArrayList<Workout_Module> userArray = gson.fromJson(assessment_Module.getObject_list(), userListType);
//			if (userArray != null && !userArray.isEmpty())
//				workout_module_info_Repository.saveAll(userArray);
//
//			/*
//			 * Workout_Module[] pp1 = mapper.readValue(assessment_Module.getObject_list(),
//			 * Workout_Module[].class);
//			 * 
//			 * for (Workout_Module person : pp1) { logger.info(person); }
//			 * 
//			 * 
//			 */
//
//			/*
//			 * for(String dc:data) { logger.info("DC   :: "+dc); }
//			 */
//			/*
//			 * for( Object result:assessment_Module.getObject_list()) {
//			 * 
//			 * }
//			 */
//
//			/*
//			 * if (assessment_Module.getWorkout_Planner() != null &&
//			 * assessment_Module.getClient_grouping() != null &&
//			 * assessment_Module.getRep_Time() != null && assessment_Module.getWorkout() !=
//			 * null) { Timestamp now = new Timestamp(System.currentTimeMillis());
//			 * assessment_Module.setCreate_date(now);
//			 * 
//			 */
//			// assessment_Module
//
//			// if(workout_module_info_Repository.find_Assement_Information_Workout_Planner(assessment_Module.getWorkout_Planner()).isEmpty())
//			/*
//			 * {
//			 * 
//			 * }
//			 */
//			return hawk_json_object;
//			// }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Workout module list.
//	 *
//	 * @return the hawk json object
//	 */
//	@PostMapping("/workout_module_list")
//	public Hawk_Json_Object workout_module_list() {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			Map<String, Object> result = new TreeMap();
//			result.put("workout_module", workout_module_info_Repository.find_Assement_Information_by_client_grouping());
//			result.put("workout_list", work_Group_Info_Repository.find_all_workout());
//			hawk_json_object.setSuporting_data(result);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Workout planner delete.
//	 *
//	 * @param workout_panner the workout panner
//	 * @return the hawk json object
//	 */
//	@GetMapping("/workout_panner_delete/{id}")
//	public Hawk_Json_Object workout_planner_delete(@PathVariable(value = "id") String workout_panner) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		logger.info(workout_panner);
//			if (workout_panner != null) {
//
//				List<Client_Quick_View> result = client_quick_view_repository
//.find_client_Workout_Planner(workout_panner);
//				if (result == null || result.isEmpty()) {
//					workout_module_info_Repository.delete_by_workout_planner_data(workout_panner);
//					hawk_json_object.setStatus("1");
//				} else {
//					hawk_json_object.setStatus("0");
//				}
//
//			} else {
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Insanity workout module delete.
//	 *
//	 * @param workout_module the workout module
//	 * @return the hawk json object
//	 */
//	@GetMapping("/insanity_workout_module_delete/{id}")
//	public Hawk_Json_Object insanity_workout_module_delete(@PathVariable(value = "id") Long workout_module) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			logger.info("workout_module :"+workout_module);
//			if (workout_module != null) {
//				assement_Information_Repository.deleteById(workout_module);
//				hawk_json_object.setStatus("1");
//			} else {
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Workout delete.
//	 *
//	 * @param workout the workout
//	 * @return the hawk json object
//	 */
//	@GetMapping("/workout_delete/{id}")
//	public Hawk_Json_Object workout_delete(@PathVariable(value = "id") Long workout) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		logger.info("workout_delete   :: " + workout);
//			if (workout != null) {
//
//				workout_module_info_Repository.deleteById(workout);
//				hawk_json_object.setStatus("1");
//			}
//			hawk_json_object.setStatus("0");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Workout update.
//	 *
//	 * @param workout_update the workout update
//	 * @return the hawk json object
//	 */
//	@PostMapping("/workout_update")
//	public Hawk_Json_Object workout_update(Workout_Module workout_update) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		if (workout_update.getId() != null) {
//				Workout_Module workout = workout_module_info_Repository.getOne(workout_update.getId());
//				if (workout_update.getRep_Time() != null && !workout_update.getRep_Time().equals(workout.getRep_Time())
//						|| workout_update.getWork_group() != null
//								&& !workout_update.getWork_group().equals(workout.getWork_group())
//						|| workout_update.getWorkout() != null
//								&& !workout_update.getWorkout().equals(workout.getWorkout())
//						|| workout_update.getDay() != null && !workout_update.getDay().equals(workout.getDay())) {
//
//					if (workout.getClient_id() != null && workout.getClone_id() != null) {
//						logger.info("UPDATE  " + workout);
//						workout_update.setClone_id(workout.getClone_id());
//						workout_update.setClient_id(workout.getClient_id());
//						workout_update.setClone_status(workout.getClone_status());
//						workout_module_info_Repository.save(workout_update);
//					} else {
//						workout_module_info_Repository.flush();
//						Workout_Module clone_workout = new Workout_Module();
//						clone_workout.setWorkout_Planner(workout_update.getWorkout_Planner());
//						clone_workout.setClient_module(workout_update.getClient_module());
//						clone_workout.setDay(workout_update.getDay());
//						clone_workout.setWork_group(workout_update.getWork_group());
//						clone_workout.setWorkout(workout_update.getWorkout());
//						clone_workout.setRep_Time(workout_update.getRep_Time());
//						clone_workout.setClient_id(workout_update.getClient_id());
//						clone_workout.setClone_id(workout_update.getId());
//						clone_workout.setClone_status("1");
//						clone_workout.setVideo_link(workout_update.getVideo_link());
//						workout_module_info_Repository.saveAndFlush(clone_workout);
//
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Master workout update.
//	 *
//	 * @param workout_update the workout update
//	 * @return the hawk json object
//	 */
//	@PostMapping("/master_workout_update")
//	public Hawk_Json_Object master_workout_update(Workout_Module workout_update) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			if (workout_update.getId() != null) {
//				Workout_Module workout = workout_module_info_Repository.getOne(workout_update.getId());
//				if (workout_update.getRep_Time() != null && !workout_update.getRep_Time().equals(workout.getRep_Time())
//						|| workout_update.getWork_group() != null
//								&& !workout_update.getWork_group().equals(workout.getWork_group())
//						|| workout_update.getWorkout() != null
//								&& !workout_update.getWorkout().equals(workout.getWorkout())
//						|| workout_update.getDay() != null && !workout_update.getDay().equals(workout.getDay())) {
//					{
//						workout_update.setClient_module(workout.getClient_module());
//						workout_update.setClone_id(workout.getClone_id());
//						workout_update.setClient_id(workout.getClient_id());
//						workout_update.setClone_status(workout.getClone_status());
//						logger.info("UPDATE workout_update  " + workout_update);
//						workout_module_info_Repository.save(workout_update);
//					}
//
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Assement information module entry.
//	 *
//	 * @param assessment_Module the assessment module
//	 * @return the hawk json object
//	 */
//	@PostMapping("/assement_information_module_entry")
//	public Hawk_Json_Object assement_information_module_entry(Assement_Information assessment_Module) {
//		 now = new Timestamp(System.currentTimeMillis());
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//			if (assessment_Module.getAssement_Type() != null && assessment_Module.getTest() != null) {
//				assessment_Module.setCreate_date(now);
//				// if(assement_Information_Repository.find_Assement_Information_type(assessment_Module.getAssement_Type()).size()<0)
//				{
//					Assement_Information res = assement_Information_Repository.save(assessment_Module);
//					hawk_json_object.setData(res);
//				}
//				return hawk_json_object;
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Assement information module list.
//	 *
//	 * @return the hawk json object
//	 */
//	@PostMapping("/assement_information_module_list")
//	public Hawk_Json_Object assement_information_module_list() {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			List<Assement_Information> res = assement_Information_Repository.find_Assement_Information_all();
//			logger.info("Assement_Information :: " + res);
//			hawk_json_object.setData(res);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Client information.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/client_information")
//	public Hawk_Json_Object client_information(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			Map<String, Object> result = new TreeMap();
//			Assessment_Module client_list = new Assessment_Module();
//			client_list.setClient_id((String) request.getSession().getAttribute("client_id"));
//			/* client_list.setClient_id("H0202030"); */
//			
//			logger.info("client_id  :"+(String) request.getSession().getAttribute("client_id"));
//			if (client_list.getClient_id() != null) {
//				Management_Info client_details = management_info_repository.find_by_search(
//						client_list.getClient_id(), client_list.getClient_id());
//				result.put("assessment_module",assessment_Module_Repository.find_Assement_Information_client_id(client_list.getClient_id()));
//				result.put("assessment_info",assement_Information_Repository.find_Assement_Information_all());
//				Client_Quick_View quick_view = client_details.getClient_Quick_View();
//				quick_view.setStretching(quick_view.getStretching());
//				quick_view.setWarm_ups(quick_view.getWarm_ups());
//				quick_view.setWorkout_Chart(quick_view.getWorkout_Chart());
//				result.put("quick_view", quick_view);
//				logger.info("quick_view   ::::: " + quick_view);
//				result.put("work_out_details", workout_module_info_Repository.find_Assement_Information_Workout_Planner(quick_view.getWarm_ups(), quick_view.getWorkout_Chart(), quick_view.getStretching()));
//
//				logger.info("work_out_details   :: " + result.get("work_out_details"));
//
//				result.put("attendance_Info", attendance_Information_Repository.find_Assement_Information_type(client_details.getClient_id()));
//				result.put("calendar_event", calendar_Information_Repository.findAll());
//				result.put("friday_challenge_info", friday_Challenge_Info_Repository
//						.friday_Challenge_Info_find_by_day(client_details.getClient_id()));
//				result.put("client_friday_challenge_info",
//						client_Friday_Challenge_Info_Repository.find_by_client_id(client_list.getClient_id()));
//				hawk_json_object.setSuporting_data(result);
//				hawk_json_object.setData(client_details);
//			}
//
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	
//	/**
//	 * Attendance by client.
//	 *
//	 * @param request the request
//	 * @param client_id the client id
//	 * @return the hawk json object
//	 */
//	@PostMapping("/attendance_by_client")
//	public Hawk_Json_Object attendance_by_client(HttpServletRequest request,String client_id) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			Map<String, Object> result = new TreeMap();
//			Assessment_Module client_list = new Assessment_Module();
//			client_list.setClient_id(client_id);
//			if (client_list.getClient_id() != null) {
//				result.put("attendance_Info", attendance_Information_Repository.find_Assement_Information_type(client_list.getClient_id()));
//				result.put("calendar_event", calendar_Information_Repository.findAll());
//				hawk_json_object.setSuporting_data(result);
//			}
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	/**
//	 * Calendar info entry.
//	 *
//	 * @param calendar_Information the calendar information
//	 * @return the hawk json object
//	 */
//	@PostMapping("/calendar_Info_entry")
//	public Hawk_Json_Object calendar_Info_entry(Calendar_Information calendar_Information) {
//			 now = new Timestamp(System.currentTimeMillis());
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//
//			if (calendar_Information.getMessage() != null&&calendar_Information.getTitle()!=null) {
//				if(calendar_Information.getId()==null)
//					calendar_Information.setCreate_Date(now);
//				hawk_json_object.setData(calendar_Information_Repository.save(calendar_Information));
//				hawk_json_object.setStatus("1");
//				}
//				else
//				{
//					hawk_json_object.setStatus("0");
//				}
//		} catch (Exception e) {
//			e.printStackTrace();
//			hawk_json_object.setStatus("0");
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Calendar info delete.
//	 *
//	 * @param id the id
//	 * @return the hawk json object
//	 */
//	@GetMapping("/calendar_Info_delete/{id}")
//	public Hawk_Json_Object calendar_Info_delete(@PathVariable(value = "id") Long id) {
//			 now = new Timestamp(System.currentTimeMillis());
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;			
//			if (id!=null){
//				calendar_Information_Repository.deleteById(id);
//				hawk_json_object.setStatus("1");
//				}
//				else
//				{
//					hawk_json_object.setStatus("0");
//				}
//		} catch (Exception e) {
//			e.printStackTrace();
//			hawk_json_object.setStatus("0");
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Calendar info report.
//	 *
//	 * @param calendar_Information the calendar information
//	 * @return the hawk json object
//	 */
//	@PostMapping("/calendar_Info_report")
//	public Hawk_Json_Object calendar_Info_report(Calendar_Information calendar_Information) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//	hawk_json_object.setData(calendar_Information_Repository.findAll());	
//		} catch (Exception e) {
//			e.printStackTrace();
//			hawk_json_object.setStatus("0");
//		}
//		return hawk_json_object;
//	}
//	
//	
//	
//	
//	/**
//	 * Client workout day.
//	 *
//	 * @param client_Workout_Info the client workout info
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	/*	  
//	
//	
//	
//	@PostMapping("/client_Workout_day")
//	public Hawk_Json_Object client_Workout_day(Client_Workout_Info client_Workout_Info, HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			List<Client_Workout_Info> client_Workout = client_Workout_Info_Repository.findby_Workout_Planner(
//					client_Workout_Info.getClient_id(), client_Workout_Info.getWorkout_Planner());
//			if (client_Workout != null && !client_Workout.isEmpty()) {
//				hawk_json_object.setStatus(String.valueOf(Integer.parseInt(client_Workout.get(0).getDay()) + 1));
//			} else {
//				List<Client_Workout_Info> client_Workout1 = client_Workout_Info_Repository
//						.findby_Workout_Planner_status(client_Workout_Info.getClient_id(),
//								client_Workout_Info.getWorkout_Planner());
//				if (client_Workout1 != null && !client_Workout1.isEmpty()) {
//					hawk_json_object.setStatus("0");
//				} else {
//					hawk_json_object.setStatus("1");
//				}
//			}
//			List<Workout_Module> workout_result = workout_module_info_Repository
//					.find_Assement_Information_Workout_Planner_day(client_Workout_Info.getWorkout_Planner(),
//							hawk_json_object.getStatus());
//			
//if(workout_result==null||workout_result.isEmpty()) {
//	hawk_json_object.setStatus("0");
//	workout_result = workout_module_info_Repository
//			.find_Assement_Information_Workout_Planner_day(client_Workout_Info.getWorkout_Planner(),
//					hawk_json_object.getStatus());
//}
//			logger.info("workout_result  :: " + workout_result);
//			logger.info("client_Workout_Info  :: " + client_Workout_Info);
//
//			List<Workout_Module> longnames = workout_result.stream()
//					.filter(str -> str != null && str.getClient_id() != null
//							&& str.getClient_id().equals(client_Workout_Info.getClient_id()))
//					.collect(Collectors.toList());
//			logger.info("longnames   :: " + longnames);
//			Set<Long> clone_id_list = longnames.stream().map(Workout_Module::getClone_id).collect(Collectors.toSet());
//			logger.info("clone_id_list   :: " + clone_id_list);
//			List<Workout_Module> listOutput = workout_result.stream().filter(e -> !clone_id_list.contains(e.getId()))
//					.collect(Collectors.toList());
//			logger.info("listOutput   :: " + listOutput);
//
//			hawk_json_object.setData(listOutput);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//*/
//	@PostMapping("/client_Workout_day")
//	public Hawk_Json_Object client_Workout_day(Client_Workout_Info client_Workout_Info, HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {			 
//				 
//				 List<Workout_Module> workout_List=new ArrayList<Workout_Module>();
//				 
//				 Client_Workout_Info client_Workout = client_Workout_Info_Repository.findby_Client_Workout_List(client_Workout_Info.getClient_id(),client_Workout_Info.getWorkout_Planner());
//				if (client_Workout != null) {	
//					
//					hawk_json_object.setStatus(String.valueOf(Integer.parseInt(client_Workout.getDay())+1));
//					workout_List = workout_module_info_Repository.find_Assement_Information_Workout_Planner_day(client_Workout_Info.getWorkout_Planner(),hawk_json_object.getStatus());
//					if(workout_List==null ||workout_List.isEmpty())
//					{
//						hawk_json_object.setStatus("1");
//						workout_List = workout_module_info_Repository.find_Assement_Information_Workout_Planner_day(client_Workout_Info.getWorkout_Planner(),hawk_json_object.getStatus());
//					}
//					
//					Date d1 = new Date(client_Workout.getCreate_date().getTime());
//					Date d2 = new Date();
//					long diff = d2.getTime() - d1.getTime();
//					long diffHours = diff / (60 * 60 * 1000);
//					if(diffHours<12)
//					{
//						hawk_json_object.setView(" Waiting For Workout");
//						workout_List.clear();
//					}
//					
//				} else {	
//					
//					hawk_json_object.setStatus(String.valueOf("1"));
//					workout_List = workout_module_info_Repository.find_Assement_Information_Workout_Planner_day(client_Workout_Info.getWorkout_Planner(),hawk_json_object.getStatus());
//					
//				}
//			
//
//			hawk_json_object.setData(workout_List);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * Client workout entry.
//	 *
//	 * @param client_Workout_Info the client workout info
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/client_Workout_entry")
//	public Hawk_Json_Object client_Workout_entry(Client_Workout_Info client_Workout_Info, HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		 now = new Timestamp(System.currentTimeMillis());
//		try {
//			if(!request.getSession().getAttribute("client_id").equals(""))
//			{
//			client_Workout_Info.setClient_id(String.valueOf(request.getSession().getAttribute("client_id")));
//			if (client_Workout_Info.getClient_id() != null && client_Workout_Info.getCompletion() != null) {
//				client_Workout_Info.setCreate_date(now);
//				client_Workout_Info.setWorkout_date(now);
//				Client_Workout_Info res = client_Workout_Info_Repository.save(client_Workout_Info);
//				hawk_json_object.setData(res);
//				hawk_json_object.setStatus("1");
//				return hawk_json_object;
//			}
//			}
//			else
//			{
//				hawk_json_object.setStatus("0");
//				hawk_json_object.setView("client");
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//
//	/**
//	 * Client friday challenge entry.
//	 *
//	 * @param client_Workout_Info the client workout info
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/client_Friday_Challenge_entry")
//	public Hawk_Json_Object client_Friday_Challenge_entry(Client_Friday_Challenge_Info client_Workout_Info,
//			HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		 now = new Timestamp(System.currentTimeMillis());
//		try {
//			client_Workout_Info.setClient_id(String.valueOf(request.getSession().getAttribute("client_id")));
//			if (client_Workout_Info.getClient_id() != null && client_Workout_Info.getCompetition() != null) {
//				client_Workout_Info.setCreate_date(now);
//				Date date = (new SimpleDateFormat("dd/MM/yyyy").parse(client_Workout_Info.getEvent_date()));
//				Timestamp ew_timestamp = new Timestamp(date.getTime());
//				client_Workout_Info.setEvent_date(ew_timestamp.toString());
//				Client_Friday_Challenge_Info res = client_Friday_Challenge_Info_Repository.save(client_Workout_Info);
//				hawk_json_object.setData(res);
//				return hawk_json_object;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Client quick view list.
//	 *
//	 * @return the hawk json object
//	 */
//	@PostMapping("/client_quick_view_list")
//	public Hawk_Json_Object client_quick_view_list() {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//
//			Map<String, Object> result = new TreeMap();
//			result.put("client_quick_view", managementInfoActiveClients());
//			result.put("wod", workout_module_info_Repository.find_Assement_Information_by_client_grouping());
//			hawk_json_object.setData(result);
//			return hawk_json_object;
//		} catch (Exception e) {
//			//e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Quick view update.
//	 *
//	 * @param client_Quick_View the client quick view
//	 * @param update_code the update code
//	 * @return the hawk json object
//	 */
//	/*
//	 * @PostMapping("/client_Friday_Challenge_entry") public Hawk_Json_Object
//	 * client_Friday_Challenge_entry(Client_Friday_Challenge_Info
//	 * client_Workout_Info) { Hawk_Json_Object hawk_json_object=new
//	 * Hawk_Json_Object(); try {
//	 * logger.info("client_Workout_Info   :: "+client_Workout_Info);
//	 * if(client_Workout_Info.getClient_id()!=null&&client_Workout_Info.
//	 * getCompetition()!=null) { Timestamp now = new
//	 * Timestamp(System.currentTimeMillis());
//	 * client_Workout_Info.setCreate_date(now); Client_Friday_Challenge_Info res =
//	 * client_Friday_Challenge_Info_Repository.save(client_Workout_Info);
//	 * hawk_json_object.setData(res); return hawk_json_object; } } catch (Exception
//	 * e) { e.printStackTrace(); } return null; }
//	 */
//	@PostMapping("/quick_view_update")
//	public Hawk_Json_Object quick_view_update(Client_Quick_View client_Quick_View,String update_code) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			 now = new Timestamp(System.currentTimeMillis());
//			logger.info("update_code :: " + update_code);
//			logger.info("quick_view_update :: " + client_Quick_View);
//			if (client_Quick_View.getClient_id() != null&&update_code!=null) {
//				Management_Info client_Quick_View_details = management_info_repository.find_by_search( client_Quick_View.getClient_id(),
//						client_Quick_View.getClient_id());
//				if (client_Quick_View_details!=null) {
//					Client_Quick_View quick_View = client_Quick_View_details.getClient_Quick_View();
//					       
//					if(update_code.equals("1"))
//					{
//						if(client_Quick_View.getWorkout_Chart()!=null)
//							quick_View.setWorkout_Chart(client_Quick_View.getWorkout_Chart());
//							quick_View.setStretching(client_Quick_View.getStretching());
//							quick_View.setWarm_ups(client_Quick_View.getWarm_ups());
//							quick_View.setClient_Status(client_Quick_View.getClient_Status());
//					}
//					
//					else if(update_code.equals("2"))
//					{
//						quick_View.setFeedback(client_Quick_View.getFeedback());	
//					}
//					else if(update_code.equals("3"))
//					{
//						quick_View.setGoogle_review(client_Quick_View.getGoogle_review());	
//					}
//							quick_View.setUpdate_Date(now);
//						client_quick_view_repository.save(quick_View);
//						hawk_json_object.setStatus("1");
//					}
//				else
//				{
//					hawk_json_object.setStatus("0");
//				}
//			}
//			else
//			{
//				hawk_json_object.setStatus("0");
//			}
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//			hawk_json_object.setStatus("0");
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Feedback update.
//	 *
//	 * @param feedback_Info the feedback info
//	 * @param update_code the update code
//	 * @return the hawk json object
//	 */
//	////FeedBack
//	@PostMapping("/feedback_update")
//	public Hawk_Json_Object feedback_update(Feedback_Info feedback_Info,String update_code) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;		
//			if(feedback_Info!=null&&feedback_Info.getClient_id()!=null&&feedback_Info.getFeedback_category()!=null&&!feedback_Info.getFeedback_category().isEmpty())
//			{
//				if(feedback_Info.getId()!=null)
//				{
//					Feedback_Info feedback_InfoFromDB = feedback_Info_Repository.getOne(feedback_Info.getId());
//				if(feedback_InfoFromDB!=null)
//				{
//					feedback_Info.setId(feedback_InfoFromDB.getId());
//					feedback_Info.setCreate_date(feedback_InfoFromDB.getCreate_date());
//					feedback_Info.setUpdate_date(new Timestamp(System.currentTimeMillis()));
//				}
//				}
//				else
//				feedback_Info.setCreate_date(new Timestamp(System.currentTimeMillis()));
//					Management_Info management_info = management_info_repository.find_by_client_id(feedback_Info.getClient_id());
//					management_info.setFeedback_Info(feedback_Info);
//					management_info_repository.save(management_info);
//					hawk_json_object.setStatus("1");
//
//
//			}
//			else
//				hawk_json_object.setStatus("3");		
//		} catch (Exception e) {
//			e.printStackTrace();
//			hawk_json_object.setStatus("0");
//		}
//		return hawk_json_object;
//	}
//
///**
// * Feedback report.
// *
// * @return the hawk json object
// */
////Feedback report
//	@PostMapping("/feedback_report")
//	@ResponseBody
//	public Hawk_Json_Object feedback_report() {
//		logger.info("feedback_report method called...");
//		try {
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;	
//				else if(hawk_Login.getUser_group()==superUser)
//				{
//			hawk_json_object.setData(feedbackFollowUpList());
//			hawk_json_object.setSuporting_data(management_info_repository.findAll());
//			return hawk_json_object;
//				}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * Feedback follow up list.
//	 *
//	 * @return the list
//	 * @throws ParseException the parse exception
//	 */
//	public List<Management_Info> feedbackFollowUpList() throws ParseException
//	{ 
//		try {
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return null;		
//			else if(hawk_Login.getUser_group()==superUser)
//		return managementInfoActiveClients().stream().filter(client->
//		(getDifferenceDays(new Date(client.getRenewal_Date().getTime()),new Date())>=30&&
//		(client.getFeedback_Info()!=null&&getDifferenceDays(new Date(),new Date(client.getFeedback_Info().getCreate_date().getTime()))>=30||client.getFeedback_Info()==null)
//		))
//		.collect(Collectors.toList());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * Feedback by client.
//	 *
//	 * @param client_id the client id
//	 * @return the hawk json object
//	 */
//	//Feedback report
//		@GetMapping("/feedback_by_client/{client_id}")
//		@ResponseBody
//		public Hawk_Json_Object feedback_by_client(String client_id) {
//			logger.info("feedback_report method called...");
//			try {
//				Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//				hawk_json_object=sessionCheck(hawk_json_object);
//				if(hawk_json_object.getSessionStatus()==false)
//					return hawk_json_object;		
//				feedback_Info_Repository.feedback_find_by_client_id(client_id);
//				List<Management_Info> management_info_List = management_info_repository.findAll();
//				hawk_json_object.setData(management_info_List);
//				return hawk_json_object;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//	
//	/**
//	 * Update client details.
//	 *
//	 * @param management_Info the management info
//	 * @return the hawk json object
//	 */
//	@PostMapping("/update_client_details")
//	public Hawk_Json_Object update_client_details(Management_Info management_Info) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			logger.info("update_client_details Management_Info   : " + management_Info.toString());
//			if (management_Info.getClient_id() != null &&management_Info.getClient_Name() != null && management_Info.getMobile_No() != null&& management_Info.getDOB() != null) 
//					{
//				Timestamp new_timestamp = new Timestamp(System.currentTimeMillis());
//				Date date=new Date();
//				Calendar cal = Calendar.getInstance();
//				Calendar now = Calendar.getInstance();
//				Management_Info result = management_info_repository.find_by_client_id_for_active(management_Info.getClient_id());
//				if (result != null) {
//					 date=(result.getRenewal_Date());
//					if(management_Info.getCus_date()!=null)
//					{
//						 date=new SimpleDateFormat("dd/MM/yyyy").parse(management_Info.getCus_date());
//						new_timestamp = new Timestamp(date.getTime());
//						now.setTime(date);
//					}
//
//					cal.setTime(date);
//					cal.add(Calendar.MONTH, Integer.parseInt(management_Info.getPayment_History().getPackage_Duration().split(" ")[0]));
//					if(management_Info.getPayment_History().getExtension()!=null&&!management_Info.getPayment_History().getExtension().isEmpty())
//					cal.add(Calendar.DATE, Integer.parseInt(management_Info.getPayment_History().getExtension()));
//					java.util.Date expirationDate = cal.getTime();
//					
//					result.setClient_Name(management_Info.getClient_Name());
//					result.setMobile_No(management_Info.getMobile_No());
//					result.setAddress(management_Info.getAddress());
//					result.setDOB(management_Info.getDOB());
//					result.setGender(management_Info.getGender());
//					result.setEmail(management_Info.getEmail());
//					result.setFacebook_Id(management_Info.getFacebook_Id());
//					result.setInstagram_Id(management_Info.getInstagram_Id());
//					result.setReferral_Source(management_Info.getReferral_Source());
//					result.setReferral(management_Info.getReferral());
//					result.setBody_Type(management_Info.getBody_Type());
//					result.setType(management_Info.getType());
//					result.setWeight(management_Info.getWeight());
//					result.setPARQ(management_Info.getPARQ());
//					result.setRemarks(management_Info.getRemarks());
//					
//					result.setUpdate_Date(new_timestamp);
//					result.setUpdatedBy(management_Info.getClient_id());
//					result.getPayment_History().setRenewal_Date(new Timestamp(expirationDate.getTime()));
//					result.getClient_Quick_View().setEnd_Date(result.getPayment_History().getRenewal_Date());
//					result.getClient_Quick_View().setDuration((String.valueOf(ChronoUnit.DAYS.between(result.getClient_Quick_View().getStart_Date().toLocalDateTime(),result.getClient_Quick_View().getEnd_Date().toLocalDateTime()))));
//					result.getClient_Quick_View().setUpdate_Date(new_timestamp);
//					result.getPayment_History().setCategory(management_Info.getPayment_History().getCategory());
//					result.getPayment_History().setExtension(management_Info.getPayment_History().getExtension());
//					result.getPayment_History().setPackage_Duration(management_Info.getPayment_History().getPackage_Duration());
//					
//					
//					
//					result.getClient_Quick_View().setClient_Status(management_Info.getClient_Quick_View().getClient_Status());
//					result.getClient_Quick_View().setWarm_ups(management_Info.getClient_Quick_View().getWarm_ups());
//					result.getClient_Quick_View().setStretching(management_Info.getClient_Quick_View().getStretching());
//					if(management_Info.getClient_Quick_View().getWorkout_Chart()!=null)
//					result.getClient_Quick_View().setWorkout_Chart(management_Info.getClient_Quick_View().getWorkout_Chart());
//					result.getPayment_History().setTarget(management_Info.getPayment_History().getTarget());
//				//	result.getPayment_History().(result.getUpdate_Date());
//					
//					
//					
//					
//					
//					
//					management_info_repository.save(result);
//					hawk_json_object.setStatus("1");
//					return hawk_json_object;
//				}
//			}
//			else
//			{
//				hawk_json_object.setStatus("0");
//			}
//		} catch (Exception e) {
//			hawk_json_object.setStatus("0");
//			e.printStackTrace();
//		}
//		return hawk_json_object;
//	}
//	
//	/**
//	 * Quick view report.
//	 *
//	 * @param request the request
//	 * @return the hawk json object
//	 */
//	@PostMapping("/client_report")
//	public Hawk_Json_Object quick_view_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//			hawk_json_object.setData(management_info_repository.findAll());
//			hawk_json_object.setSuporting_data(workout_module_info_Repository.find_Assement_Information_by_client_grouping());
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//	
//	
//	/**
//	 * Attendance report.
//	 *
//	 * @param att_date the att date
//	 * @param model the model
//	 * @return the hawk json object
//	 */
//	@PostMapping("/attendance_report")
//	@ResponseBody
//	public Hawk_Json_Object attendance_report(String  att_date, Model model) {
//		try {
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			logger.info("att_date   "+att_date);
//			
//			String pattern = "yyyy-MM-dd";
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//			if(att_date!=null&&!att_date.equals(""))
//				att_date=simpleDateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(att_date));
//			else
//				att_date=simpleDateFormat.format(new Date());
//			 //date=simpleDateFormat.parse(date);
//			logger.info("att_date "+att_date);
//			
//			Map<String, Object> result = new TreeMap();
//			result.put("client_quick_view", management_info_repository.attendance_by_today(att_date));
//			result.put("wod", workout_module_info_Repository.find_Assement_Information_by_client_grouping());
//			hawk_json_object.setData(result);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * Attendance leave report.
//	 *
//	 * @param att_date the att date
//	 * @param model the model
//	 * @return the hawk json object
//	 */
//	@PostMapping("/attendance_leave_report")
//	@ResponseBody
//	public Hawk_Json_Object attendance_leave_report(String  att_date, Model model) {
//		try {
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			Map<String, Object> result = new TreeMap();
//			List<Management_Info> managementList =new ArrayList<Management_Info>() ;
//			
//			for(Management_Info management_Info:managementInfoActiveClients())
//			{
//				
//				 Attendance_Info attendance_Info = attendance_Information_Repository.attendance_by_currentmoth_clientid( management_Info.getClient_id());
//							if(attendance_Info!=null)
//							{
//								int leave_count=(int) getDifferenceDays(new Date((attendance_Info.getCreate_date()).getTime()),new Date((new Timestamp(System.currentTimeMillis())).getTime()));
//								if(leave_count>3)
//								{
//									management_Info.setLeave_count(leave_count);
//									managementList.add(management_Info);
//								}	
//							}
//				
//				else {
//					int leave_count=(int) getDifferenceDays(new Date((management_Info.getCreate_Date()).getTime()),new Date((new Timestamp(System.currentTimeMillis())).getTime()));
//					if(leave_count>=3)
//					{
//						management_Info.setLeave_count(leave_count);
//						managementList.add(management_Info);
//					}					}
//				}
//			result.put("client_quick_view",managementList);
//			
//			hawk_json_object.setData(result);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * Workout planner base.
//	 *
//	 * @param planner the planner
//	 * @param model the model
//	 * @return the hawk json object
//	 */
//	@PostMapping("/workout_Planner_base_report/{planner}")
//	public Hawk_Json_Object workout_Planner_base(@PathVariable("planner") String planner, Model model) {
//		try {
//			logger.info("planner Rest : " + planner);
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			hawk_json_object.setData(workout_module_info_Repository.find_Assement_Information_workout_Planner_base(planner));
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	/**
//	 * Workout report.
//	 *
//	 * @param planner the planner
//	 * @param client_id the client id
//	 * @param model the model
//	 * @return the hawk json object
//	 */
//	@PostMapping("/workout_report/{planner}/{client_id}")
//	public Hawk_Json_Object workout_report(@PathVariable("planner") String planner,
//			@PathVariable("client_id") String client_id, Model model) {
//		try {
//			Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//			Map<String, Object> result = new TreeMap();
//			List<Workout_Module> workout_result = workout_module_info_Repository
//					.find_Assement_Information_Workout_Planner_name(planner);
//			List<Workout_Module> longnames = workout_result.stream()
//					.filter(str -> str != null && str.getClient_id() != null && str.getClient_id().equals(client_id))
//					.collect(Collectors.toList());
//		//	logger.info("longnames   :: " + longnames);
//			Set<Long> clone_id_list = longnames.stream().map(Workout_Module::getClone_id).collect(Collectors.toSet());
//			//logger.info("clone_id_list   :: " + clone_id_list);
//			List<Workout_Module> listOutput = workout_result.stream().filter(e -> !clone_id_list.contains(e.getId()))
//					.collect(Collectors.toList());
//			//logger.info("listOutput   :: " + listOutput);
//						List<Client_Workout_Info> client_Workout_InfoList = client_Workout_Info_Repository.findby_clientId(client_id);
//						result.put("workout_list_by_client",client_Workout_InfoList);
//
//			//logger.info("result  "+client_Workout_InfoList);
//						result.put("attendance_Info", attendance_Information_Repository.find_Assement_Information_type(client_id));
//						result.put("calendar_event", calendar_Information_Repository.findAll());
//
//			
//			result.put("assessment_module",assessment_Module_Repository.find_Assement_Information_client_id(client_id));
//			result.put("assessment_info",assement_Information_Repository.find_Assement_Information_all());
//
//			hawk_json_object.setSuporting_data(result);
//			hawk_json_object.setData(listOutput);
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
////	@PostMapping("/feedback_report")
////	public Hawk_Json_Object feedback_report(HttpServletRequest request) {
////		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
////		try {
////			hawk_json_object=sessionCheck(hawk_json_object);
////			if(hawk_json_object.getSessionStatus()==false)
////				return hawk_json_object;			
////			hawk_json_object.setData(feedBackList());
////			return hawk_json_object;
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		return null;
////	}
//
//	/**
// * Google review report.
// *
// * @param request the request
// * @return the hawk json object
// */
//@PostMapping("/google_review_report")
//	public Hawk_Json_Object google_review_report(HttpServletRequest request) {
//		Hawk_Json_Object hawk_json_object = new Hawk_Json_Object();
//		try {
//			hawk_json_object=sessionCheck(hawk_json_object);
//			if(hawk_json_object.getSessionStatus()==false)
//				return hawk_json_object;			
//		hawk_json_object.setData(googleReviewkList());
//			return hawk_json_object;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	
//	    /**
//    	 * Session check.
//    	 *
//    	 * @param hawk_json_object the hawk json object
//    	 * @return the hawk json object
//    	 */
//    	public Hawk_Json_Object sessionCheck(Hawk_Json_Object hawk_json_object) {
//	    	logger.info("sessionCheck...");
//	    	if(hawk_Login!=null) {
//	    			 hawk_json_object.setSessionStatus(true);
//	    			 hawk_json_object.setUser_roll(hawk_Login.getUser_type());
//	    			 hawk_json_object.setUserName(hawk_Login.getUser_name());
//	    	}
//	    		 else
//	    			 hawk_json_object.setSessionStatus(false);
//	    		 
//	    		 return hawk_json_object;
//	        // Invalidate the session and this will clear the data from the configured database.
//	   	      //  request.getSession().invalidate();
//	    }
//
//	/*
//	 * @GetMapping("/xls_update/{id}")
//	 * 
//	 * public Hawk_Json_Object xls_update(@PathVariable(value = "id") int page_id) {
//	 * try { try { FileInputStream file = new FileInputStream(new
//	 * File("Workout replanning.xlsx"));
//	 * 
//	 * // Create Workbook instance holding reference to .xlsx file XSSFWorkbook
//	 * workbook = new XSSFWorkbook(file);
//	 * 
//	 * // Get first/desired sheet from the workbook XSSFSheet sheet =
//	 * workbook.getSheetAt(page_id);
//	 * 
//	 * // Iterate through each rows one by one Iterator<Row> rowIterator =
//	 * sheet.iterator();
//	 * 
//	 * while (rowIterator.hasNext()) { Row row = rowIterator.next(); Iterator<Cell>
//	 * cellIterator = row.cellIterator(); while (cellIterator.hasNext()) { Cell cell
//	 * = cellIterator.next();
//	 * 
//	 * 
//	 * Work_Group work_Group=new Work_Group();
//	 * work_Group.setWorkout(cell.getStringCellValue());
//	 * if(work_Group.getWorkout()!=null&&!work_Group.getWorkout().isEmpty()) try {
//	 * work_Group_Info_Repository.save(work_Group); } catch (Exception e) { // TODO:
//	 * handle exception } } } file.close();
//	 * 
//	 * 
//	 * 
//	 * } catch (Exception e) { e.printStackTrace(); } } catch (Exception e) {
//	 * e.printStackTrace(); } return null; }
//	 */
//	
//	/**
//	 * Month and year between.
//	 *
//	 * @param timestamp the timestamp
//	 * @return true, if successful
//	 * @throws ParseException the parse exception
//	 */
//	public boolean monthAndYearBetween(String timestamp) throws ParseException
//	{
//		Date date=new SimpleDateFormat("yyyy-MM").parse(timestamp);
//	    Calendar cal=Calendar.getInstance();
//	    cal.setTime(date);
//	    Calendar calNow=Calendar.getInstance();
//	    if(cal.get(Calendar.YEAR)<=calNow.get(Calendar.YEAR)&&cal.get(Calendar.MONTH)<=calNow.get(Calendar.MONTH))
//	    	return true;
//	    else
//	    	return false;
//		
//	}
//	
//	/**
//	 * Management info active clients.
//	 *
//	 * @return the list
//	 * @throws ParseException the parse exception
//	 */
//	public List<Management_Info> managementInfoActiveClients() throws ParseException
//	{
//		return management_info_repository.findAll().stream().filter(client->client.getClient_Quick_View().getClient_Status().toLowerCase().equals(active.toLowerCase())).collect(Collectors.toList());
//	}
//	
//	/**
//	 * Feed back list.
//	 *
//	 * @return the list
//	 * @throws ParseException the parse exception
//	 */
//	public List<Management_Info> feedBackList() throws ParseException
//	{
//	return managementInfoActiveClients().stream().filter(client-> getDifferenceDays(new Date((client.getCreate_Date()).getTime()),new Date((new Timestamp(System.currentTimeMillis())).getTime()))>30&&client.getClient_Quick_View().getFeedback().equals("")).collect(Collectors.toList());
//	}
//	
//	/**
//	 * Google reviewk list.
//	 *
//	 * @return the list
//	 * @throws ParseException the parse exception
//	 */
//	public List<Management_Info> googleReviewkList() throws ParseException
//	{
//		return managementInfoActiveClients().stream().filter(client-> getDifferenceDays(new Date((client.getCreate_Date()).getTime()),new Date((new Timestamp(System.currentTimeMillis())).getTime()))>30&&client.getClient_Quick_View().getGoogle_review().equals("")).collect(Collectors.toList());
//	}
//	HawkRestController.hawk_Login
//	/**
//	 * Gets the difference days.
//	 *
//	 * @param d1 the d 1
//	 * @param d2 the d 2
//	 * @return the difference days
//	 */
//	public static long getDifferenceDays(Date d1, Date d2) {
//	    long diff = d2.getTime() - d1.getTime();
//	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//	}
//}
