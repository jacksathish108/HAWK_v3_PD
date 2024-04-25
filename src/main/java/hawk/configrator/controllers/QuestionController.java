/*
 * 
 */
package hawk.configrator.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hawk.bizservices.ExcelUtility;
import hawk.configrator.dtos.QuestionDTO;
import hawk.configrator.services.QuestionService;
import hawk.dtos.ResultMapper;

//
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
	public ResultMapper setQuestionInfo(QuestionDTO questionInfoDTO, Model model) {
		logger.info("setQuestionInfo method called..." + questionInfoDTO);
		return QuestionService.setQuestion(questionInfoDTO);
	}

	@PostMapping("/deleteQuestion")
	public ResultMapper deleteQuestionInfo(Long id, Model model) {
		logger.info("setQuestionInfo method called..." + id);
		return QuestionService.deleteQuestion(id);
	}
	
	
//	  @PostMapping("/uploadQuestion")
//	  public ResponseEntity<?> uploadFile(@RequestPart(value = "files", required = true) MultipartFile file) {
		  @PostMapping("/uploadQuestion")
		  public ResultMapper uploadFile() {
try {
	 FileInputStream file=new FileInputStream(new File("C:\\Users\\sathishkumar.K\\Downloads\\Questions.V2.xlsx")); 
	 List<QuestionDTO> ss = ExcelUtility.excelToquestionInfoList(file);
	 QuestionService.setQuestion(ss);

} catch (Exception e) {
	e.printStackTrace();
	// TODO: handle exception
}
			  
			  
			  
			  return null;
	}
}