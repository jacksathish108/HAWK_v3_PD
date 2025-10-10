/*
 * 
 */
package sudo.product.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sudo.dtos.ResultMapper;
import sudo.product.services.AnswerService;

@RestController
@RequestMapping("${ApiVersion}")
public class AnswerController {
 	Logger logger = LoggerFactory.getLogger(AnswerController.class);
 	@Autowired
 	AnswerService AnswerService;

 	@GetMapping("/getAllAnswer")
 	public ResultMapper getAnswerInfo() {
 		logger.info("getAnswerInfo method called...");
 		return AnswerService.getAnswer();
 	} 
 	@PostMapping("/getAnswersByViewId")
 	public ResultMapper getAnswersByViewId(@RequestParam Long pageId,@RequestParam Long viewId) {
 		logger.info("getAnswerInfo method called...");
 		return AnswerService.getAnswersByViewId(pageId,viewId);
 	} 
 	
@PostMapping("/setAnswer")
public ResultMapper  setAnswerInfo(@RequestParam Map<String, String> requestParams, @RequestParam(required = false) Map<String, MultipartFile> fileMap,Model model) {
		
	logger.info("setAnswerInfo method called..."+requestParams);
	return AnswerService.setAnswer(requestParams,fileMap);
}
@PostMapping("/deleteAnswer")
public ResultMapper deleteAnswerInfo(Long id, Model model) {
	logger.info("setAnswerInfo method called..."+id);
	return AnswerService.deleteAnswer(id);
}

}