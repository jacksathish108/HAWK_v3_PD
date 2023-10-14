/*
 * 
 */
package hawk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import hawk.dtos.ResultMapper;
import hawk.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class HawkRestController.
 */
@RestController
@RequestMapping("${ApiVersion}")
public class LoginController {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UsersService userService;
	////////////////////////////////////////////

	///////////////////////////////////////////
	/**
	 * Client login.
	 *
	 * @param request    the request
	 * @param hawk_Login the hawk login
	 * @return the hawk json object
	 */
//	@PostMapping("/client_login")
//	public JsonObject client_login(HttpServletRequest request, Hawk_Login hawk_Login) {
//		try {
//			logger.info("client_login method called "+hawk_Login);
//			JsonObject hawk_json_object = new JsonObject();
//			if (hawk_Login.getUser_name() != null && hawk_Login.getUser_pwd() != null) {
//				if (hawk_Login.getUser_pwd().equals(hawk_Login.getUser_name())) {
//					 Client_Info result = client_Info_Repository.find_by_user(hawk_Login.getUser_name(),0);
//					//if (result != null && result.getManagement_Info() != null) {
//						if (result != null) {
//						hawk_json_object.setStatus("1");
//						//hawk_json_object.setView("client_home");
//						hawk_json_object.setView("user_home");
//						
//						
//						hawk_json_object.setData(result);
//						request.getSession().setAttribute("data", result);
//						request.getSession().setAttribute("client_id", result.getEmail());
//						//attendance_Info_entry(result.getClient_id(), 0);
//						return hawk_json_object;
//					} else {
//						request.getSession().setAttribute("data", "null");
//						hawk_json_object.setView("");
//						hawk_json_object.setStatus("0");
//						return hawk_json_object;
//					}
//				} else {
//					request.getSession().setAttribute("data", "null");
//					hawk_json_object.setView("");
//					hawk_json_object.setStatus("0");
//					return hawk_json_object;
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}

	/**
	 * Attendance info entry.
	 *
	 * @param client_id the client id
	 * @param status    the status
	 * @return the hawk json object
	 */
	/**
	 * Client logout.
	 *
	 * @param request the request
	 * @return the hawk json object
	 */
//	@PostMapping("/client_logout")
//	public JsonObject client_logout(HttpServletRequest request) {}    
//    @RequestMapping(value="/admin", method=RequestMethod.GET)    
//    public JsonObject getUserDashbord() {
//    	logger.info("getUserDashbord method called...");
//    	return userService.getPackgeDetails()
	// }

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {
		logger.info("getUserDashbord method called...");

		return new ModelAndView("redirect:/logout");
	}

	@GetMapping("/getClientDashbord")
	public ResultMapper getClientDashbord() {
		logger.info("getUserDashbord method called...");
		return userService.getPackgeDetails();
	}
}
