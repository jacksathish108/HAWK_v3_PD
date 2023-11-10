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
import hawk.services.UsersService;
import hawk.utils.HawkResources;

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
		} else if (HawkResources.PDADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "configrator/pcHome");
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
		} else if (HawkResources.PDADMIN.equals(userService.getuserSession().getUserRole())) {
			modelAndView.setViewName(base_path + "configrator/pcHome");
			return modelAndView;

		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:login");
			return modelAndView;
		}
	}

	@RequestMapping("/view")
	public ModelAndView enquiry() {
		logger.info("view method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "configrator/viewDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/webPage")
	public ModelAndView WebPage() {
		logger.info("WebPage method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "configrator/webPageDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/question")
	public ModelAndView questionPage() {
		logger.info("WebPage method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "configrator/questionDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/clientDetails")
	public ModelAndView clientDetails() {
		logger.info("clientDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/clientDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/packageDetails")
	public ModelAndView packageDetails() {
		logger.info("packageDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/packageDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/usersDetails")
	public ModelAndView usersDetails() {
		logger.info("usersDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/usersDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/assementDetails")
	public ModelAndView assementDetails() {
		logger.info("assementDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/assementDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/pendingDueDetails")
	public ModelAndView pendingDueDetails() {
		logger.info("pendingDueDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/pendingDueDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/workoutChartDetails")
	public ModelAndView workoutChartDetails() {
		logger.info("workoutChartDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/workoutChartDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/paymentDetails")
	public ModelAndView paymentDetails() {
		logger.info("paymentDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/paymentDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/activityDetails")
	public ModelAndView activity() {
		logger.info("activity method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/activityDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/assessmentTemplateDetails")
	public ModelAndView assessmentTemplateDetails() {
		logger.info("assessmentTemplateDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/assessmentTemplateDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/assessmentDetails")
	public ModelAndView assessmentDetails() {
		logger.info("assessmentDetails method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/assessmentDetails");
		}
		return modelAndView;
	}

	@RequestMapping("/calendarEvents")
	public ModelAndView calendarEvents() {
		logger.info("calendarEvents method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/calendarEvent");
		}
		return modelAndView;
	}

	@RequestMapping("/feedbackTemplate")
	public ModelAndView feedbackTemplates() {
		logger.info("feedbackTemplates method called....");
		ModelAndView modelAndView = userSessionCheck();
		if (modelAndView.getViewName() == null) {
			modelAndView.setViewName(base_path + "admin/feedbackTemplateDetails");
		}
		return modelAndView;
	}

}
