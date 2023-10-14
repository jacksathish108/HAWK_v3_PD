/*
 * 
 */
package hawk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import hawk.dtos.ResultMapper;
import hawk.entities.UsersInfo;
import hawk.security.Provider;
import hawk.services.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class HawkRestController.
 */
@RestController
public class RegistationController {
	 
 	/** The logger. */
 	Logger logger = LoggerFactory.getLogger(RegistationController.class);
 	@Autowired
 	UsersService userService;
	
 	//@PostMapping("/clientRegistration")
 	//@RequestMapping(value = "clientRegistration", method = RequestMethod.POST)
 	
 	
	@PostMapping("/userRegistration")
	ResultMapper clientRegestration(UsersInfo usersInfo) {
		logger.info("clientRegestration method called...");
		usersInfo.setRegistrationType(Provider.LOCAL.toString());
    	return userService.userRegestration(usersInfo);
	}    
	@PostMapping("/test")
	public String testPost() {
		logger.info("test  POST  method called...");
    	return "POST  method called ";
	}    
	@GetMapping("/test")
	public String testGET() {
		logger.info("test GET method called...");
		return "GET  method called ";
	}   
}
