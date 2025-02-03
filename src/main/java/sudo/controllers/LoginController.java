/*
 * 
 */
package sudo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import sudo.user.dtos.ResultMapper;
import sudo.user.services.UsersService;

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
