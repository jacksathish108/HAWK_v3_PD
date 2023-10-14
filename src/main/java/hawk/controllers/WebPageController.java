/*
 * 
 */
package hawk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hawk.dtos.ResultMapper;
import hawk.dtos.WebPageInfoDTO;
import hawk.services.WebPageService;

@RestController
@RequestMapping("${ApiVersion}")
public class WebPageController {
 	Logger logger = LoggerFactory.getLogger(WebPageController.class);
 	@Autowired
 	WebPageService WebPageService;

 	@GetMapping("/getAllWebPage")
 	public ResultMapper getWebPageInfo() {
 		logger.info("getWebPageInfo method called...");
 		return WebPageService.getWebPage();
 	} 
@PostMapping("/setWebPage")
public ResultMapper  setWebPageInfo(WebPageInfoDTO webPageInfoDTO,Model model) {
	logger.info("setWebPageInfo method called..."+webPageInfoDTO);
	return WebPageService.setWebPage(webPageInfoDTO);
}
@PostMapping("/deleteWebPage")
public ResultMapper  deleteWebPageInfo(Long id,Model model) {
	logger.info("setWebPageInfo method called..."+id);
	return WebPageService.deleteWebPage(id);
}

}