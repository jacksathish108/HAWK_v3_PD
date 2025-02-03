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

import sudo.configrator.dtos.DataLinkDTO;
import sudo.configrator.services.DataLinkService;
import sudo.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class DataLinkController {
 	Logger logger = LoggerFactory.getLogger(DataLinkController.class);
 	@Autowired
 	DataLinkService dataLinkService;

 	@GetMapping("/getAllDataLink")
 	public ResultMapper getDataLinkInfo() {
 		logger.info("getDataLinkInfo method called...");
 		return dataLinkService.getDataLink();
 	} 
 	@GetMapping("/getAllDataLinkName")
 	public ResultMapper getAllDataLinkName() {
 		logger.info("getQuestionInfo method called...");
 		return dataLinkService.getAllDataLinkName();
 	} 
 	
 	@GetMapping("/getDataLink/{linkCode}")
 	public ResultMapper getDataLinkByViewCode(@PathVariable String linkCode) {
 		logger.info("getWebPageInfo method called...");
 		return dataLinkService.getDataLinkByLinkCode(linkCode);
 	} 
 	
 	
@PostMapping("/setDataLink")
public ResultMapper  setDataLinkInfo(DataLinkDTO dataLinkDTO,Model model) {
	logger.info("setDataLinkInfo method called..."+dataLinkDTO);
	return dataLinkService.setDataLink(dataLinkDTO);
}
@PostMapping("/deleteDataLink")
public ResultMapper  deleteDataLinkInfo(Long id,Model model) {
	logger.info("setDataLinkInfo method called..."+id);
	return dataLinkService.deleteDataLink(id);
}

}