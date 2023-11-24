/*
 * 
 */
package hawk.configrator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hawk.configrator.dtos.WebPageInfoDTO;
import hawk.configrator.services.WebPageService;
import hawk.dtos.ResultMapper;

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
 	@GetMapping("/getAllWebPage/{pageCode}")
 	public ResultMapper getWebPageInfo(@PathVariable String pageCode) {
 		logger.info("getWebPageInfo method called...");
 		return WebPageService.getWebPageInfoByCode(pageCode);
 	} 
 	@GetMapping("/getAllMenuItems")
 	public ResultMapper getAllMenuItems() {
 		logger.info("getWebPageInfo method called...");
 		return WebPageService.getPageCode();
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

@GetMapping("/getAllWebPageCode")
	public ResultMapper getAllWebPageCode() {
		logger.info("getAllWebPageCode method called...");
		return WebPageService.getAllWebPageCode();
	} 


}