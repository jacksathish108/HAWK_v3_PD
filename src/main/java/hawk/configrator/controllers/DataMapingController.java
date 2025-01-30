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

import hawk.configrator.dtos.DataMapingDTO;
import hawk.configrator.services.DataMapingService;
import hawk.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class DataMapingController {
 	Logger logger = LoggerFactory.getLogger(DataMapingController.class);
 	@Autowired
 	DataMapingService DataMapingService;

 	@GetMapping("/getAllDataMaping")
 	public ResultMapper getDataMapingInfo() {
 		logger.info("getDataMapingInfo method called...");
 		return DataMapingService.getDataMaping();
 	} 
 	@GetMapping("/getAllDataMapingName")
 	public ResultMapper getAllDataMapingName() {
 		logger.info("getQuestionInfo method called...");
 		return DataMapingService.getAllDataMapingName();
 	} 
 	
 	@GetMapping("/getDataMaping/{linkCode}")
 	public ResultMapper getDataMapingByViewCode(@PathVariable String linkCode) {
 		logger.info("getWebPageInfo method called...");
 		return DataMapingService.getDataMapingByLinkCode(linkCode);
 	} 
 	
 	
@PostMapping("/setDataMaping")
public ResultMapper  setDataMapingInfo(DataMapingDTO DataMapingDTO,Model model) {
	logger.info("setDataMapingInfo method called..."+DataMapingDTO);
	return DataMapingService.setDataMaping(DataMapingDTO);
}
@PostMapping("/deleteDataMaping")
public ResultMapper  deleteDataMapingInfo(Long id,Model model) {
	logger.info("setDataMapingInfo method called..."+id);
	return DataMapingService.deleteDataMaping(id);
}

}