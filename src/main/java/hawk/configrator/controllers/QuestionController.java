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

import hawk.configrator.dtos.QuestionDTO;
import hawk.configrator.services.QuestionService;
import hawk.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class QuestionController {
 	Logger logger = LoggerFactory.getLogger(QuestionController.class);
 	@Autowired
 	QuestionService QuestionService;

 	@GetMapping("/getAllQuestion")
 	public ResultMapper getQuestionInfo() {
 		logger.info("getQuestionInfo method called...");
 		return QuestionService.getQuestion();
 	} 
 	@GetMapping("/getAllQtag")
 	public ResultMapper getAllQtag() {
 		logger.info("getQuestionInfo method called...");
 		return QuestionService.getAllQtag();
 	} 
 	
@PostMapping("/setQuestion")
public ResultMapper  setQuestionInfo(QuestionDTO questionInfoDTO,Model model) {
	logger.info("setQuestionInfo method called..."+questionInfoDTO);
	return QuestionService.setQuestion(questionInfoDTO);
}
@PostMapping("/deleteQuestion")
public ResultMapper deleteQuestionInfo(Long id, Model model) {
	logger.info("setQuestionInfo method called..."+id);
	return QuestionService.deleteQuestion(id);
}
//@PostMapping("/excel/upload")
//public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file)
//{
//  String message = "";
//  //if (ExcelUtility.hasExcelFormat(file)) 
//  {
//    try {
//       // stuService.save(file);
//      message = "The Excel file is uploaded: " + file.getOriginalFilename();
//      return ResponseEntity.status(HttpStatus.OK).body(message);
//    } catch (Exception exp) {
//      message = "The Excel file is not upload: " + file.getOriginalFilename() + "!";
//      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
//    }
//  }
// // message = "Please upload an excel file!";
//  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
//}
}