/*
 * 
 */
package hawk.configrator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hawk.configrator.dtos.ViewDTO;
import hawk.configrator.services.ViewService;
import hawk.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class ViewController {
 	Logger logger = LoggerFactory.getLogger(ViewController.class);
 	@Autowired
 	ViewService viewService;

 	@GetMapping("/getAllView")
 	public ResultMapper getViewInfo() {
 		logger.info("getViewInfo method called...");
 		return viewService.getView();
 	} 
 	@GetMapping("/getAllViewName")
 	public ResultMapper getAllViewName() {
 		logger.info("getQuestionInfo method called...");
 		return viewService.getAllViewName();
 	} 
@PostMapping("/setView")
public ResultMapper  setViewInfo(ViewDTO viewDTO,Model model) {
	logger.info("setViewInfo method called..."+viewDTO);
	return viewService.setView(viewDTO);
}
@PostMapping("/deleteView")
public ResultMapper  deleteViewInfo(Long id,Model model) {
	logger.info("setViewInfo method called..."+id);
	return viewService.deleteView(id);
}

}