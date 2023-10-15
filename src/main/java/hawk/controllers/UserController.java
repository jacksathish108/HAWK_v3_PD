/*
 * 
 */
package hawk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hawk.entities.UsersInfo;
import hawk.jparepositorys.UsersInfoRepository;
import hawk.services.UsersService;
import hawk.utils.HawkResources;
import jakarta.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class Hawkcontroller.
 */
@Controller
public class UserController {

	static UsersInfo hawk_Login;
	/** The base path. */

	@Value("${basePath}")
	String base_path;
	@Value("${ViewVersion}")
	String viewVersion;
	@Autowired
	UsersService userService;
	@Autowired
	UsersInfoRepository users_info_Repository;
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("login")
	public ModelAndView userlogin(HttpServletRequest request) {
		logger.info("userlogin method called...");
		SecurityContextHolder.getContext().setAuthentication(null);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		logger.info("register method called....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/register");
		return modelAndView;

	}

	@RequestMapping("/")
	public ModelAndView index() {
		logger.info("index method called....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/");
		if (userService.getuserSession() == null || !userService.getuserSession().isSessionStatus()) {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("login");
			return modelAndView;
		} else if (HawkResources.CLIENT.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "user/user_home");
			return modelAndView;
		} else if (HawkResources.ADMIN.equals(userService.getuserSession().getUserRole())||
				HawkResources.SUPPERUSER.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName("redirect:"+ viewVersion+"/home");
			return modelAndView;
		}  else if (HawkResources.PDADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "configrator/pcHome");
			return modelAndView;
		}else {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
			return modelAndView ;
		}

	}

	@RequestMapping("/home")
	public ModelAndView userHome() {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("userHome method called....");
		if (userService.getuserSession() == null || !userService.getuserSession().isSessionStatus()) {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("login");
			return modelAndView;
		} else if (HawkResources.CLIENT.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "user/user_home");
			return modelAndView ;
		} else if (HawkResources.SUPPERUSER.equals(userService.getuserSession().getUserRole())||
				HawkResources.ADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName("redirect:"+ viewVersion+"/home");
			return modelAndView ;
		} else if (HawkResources.PDADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "configrator/pcHome");
			return modelAndView;
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
			return modelAndView;
		}
	}
	
	public ModelAndView userSessionCheck()
	{
		logger.info("userSessionCheck method called...");
		ModelAndView modelAndView = new ModelAndView();
		if (userService.getuserSession() == null || !userService.getuserSession().isSessionStatus()) {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
		}
		else if (!HawkResources.ADMIN.equals(userService.getuserSession().getUserRole())||
				!HawkResources.SUPPERUSER.equals(userService.getuserSession().getUserRole())) 
		{
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
		}
		return modelAndView;
		}	   
}
