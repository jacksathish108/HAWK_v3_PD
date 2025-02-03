/*
 * 
 */
package sudo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sudo.user.entities.UsersInfo;
import sudo.user.services.UsersService;
import sudo.utils.HawkResources;

// TODO: Auto-generated Javadoc
/**
 * The Class Hawkcontroller.
 */

@Controller
@RequestMapping("${ViewVersion}")
public class AdminController {

	static UsersInfo hawk_Login;
	/** The base path. */

	@Value("${basePath}")
	String base_path;
	@Autowired
	UsersService userService;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	public ModelAndView userSessionCheck() {
		logger.info("userSessionCheck method called...");
		ModelAndView modelAndView = new ModelAndView();
		if (userService.getuserSession() == null || !userService.getuserSession().isSessionStatus()) {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
		} else if (!HawkResources.ADMIN.equals(userService.getuserSession().getUserRole())
				&& !HawkResources.SUPPERUSER.equals(userService.getuserSession().getUserRole())
				&& !HawkResources.TRAINER.equals(userService.getuserSession().getUserRole())
				&& !HawkResources.PDADMIN.equals(userService.getuserSession().getUserRole())) {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
		}
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
		} else if (HawkResources.ADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "admin/adminHome");
			return modelAndView;
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
			return modelAndView;
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
			return modelAndView;

		} else if (HawkResources.SUPPERUSER.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "supperAdmin/adminHome");
			return modelAndView;

		} else if (HawkResources.ADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "admin/adminHome");
			return modelAndView;
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
			return modelAndView;
		}
	}
}
