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

import hawk.configrator.dtos.ListViewDTO;
import hawk.configrator.services.ListViewService;
import hawk.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class ListViewController {
 	Logger logger = LoggerFactory.getLogger(ListViewController.class);
 	@Autowired
 	ListViewService listViewService;

 	@GetMapping("/getAllListView")
 	public ResultMapper getListViewInfo() {
 		logger.info("getListViewInfo method called...");
 		return listViewService.getListView();
 	} 
 	@GetMapping("/getAllSelectQtags")
 	public ResultMapper getAllListViewName() {
 		logger.info("getQuestionInfo method called...");
 		return listViewService.getAllSelectQtags();
 	} 
 	
 	@GetMapping("/getListView/{code}")
 	public ResultMapper getListViewByViewCode(@PathVariable String code) {
 		logger.info("getWebPageInfo method called...");
 		return listViewService.getListViewByCode(code);
 	} 
 	
 	
@PostMapping("/setListView")
public ResultMapper  setListViewInfo(ListViewDTO listViewDTO,Model model) {
	logger.info("setListViewInfo method called..."+listViewDTO);
	return listViewService.setListView(listViewDTO);
}
@PostMapping("/deleteListView")
public ResultMapper  deleteListViewInfo(Long id,Model model) {
	logger.info("setListViewInfo method called..."+id);
	return listViewService.deleteListView(id);
}

}