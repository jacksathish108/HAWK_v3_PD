/*
 * 
 */
package sudo.configrator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sudo.configrator.dtos.DataMapingDTO;
import sudo.configrator.services.DataMappingService;
import sudo.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class DataMappingController {
 	Logger logger = LoggerFactory.getLogger(DataMappingController.class);
 	@Autowired
 	DataMappingService DataMappingService;

 	@GetMapping("/getAllDataMaping")
 	public ResultMapper getDataMapingInfo() {
 		logger.info("getDataMapingInfo method called...");
 		return DataMappingService.getDataMaping();
 	} 
 	@GetMapping("/getAllDataMapingName")
 	public ResultMapper getAllDataMapingName() {
 		logger.info("getQuestionInfo method called...");
 		return DataMappingService.getAllDataMapingName();
 	} 
 	
 	@GetMapping("/getDataMaping/{linkCode}")
 	public ResultMapper getDataMapingByViewCode(@PathVariable String linkCode) {
 		logger.info("getWebPageInfo method called...");
 		return DataMappingService.getDataMapingBydataMapCode(linkCode);
 	} 
 	
 	
@PostMapping("/setDataMaping")
public ResultMapper  setDataMapingInfo(DataMapingDTO DataMapingDTO,Model model) {
	logger.info("setDataMapingInfo method called..."+DataMapingDTO);
	return DataMappingService.setDataMaping(DataMapingDTO);
}
@PostMapping("/deleteDataMaping")
public ResultMapper  deleteDataMapingInfo(Long id,Model model) {
	logger.info("setDataMapingInfo method called..."+id);
	return DataMappingService.deleteDataMaping(id);
}

}