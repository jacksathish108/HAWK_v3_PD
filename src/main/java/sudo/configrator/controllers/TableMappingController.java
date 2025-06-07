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

import sudo.configrator.dtos.TableMappingDTO;
import sudo.configrator.services.TableMappingService;
import sudo.dtos.ResultMapper;

@RestController
@RequestMapping("${ApiVersion}")
public class TableMappingController {
	Logger logger = LoggerFactory.getLogger(TableMappingController.class);
	@Autowired
	TableMappingService TableMappingService;

	@GetMapping("/getTableMapping")
	public ResultMapper getTableMappingInfo() {
		logger.info("getTableMappingInfo method called...");
		return TableMappingService.getTableMapping();
	}

	@GetMapping("/getAllTableMappingName")
	public ResultMapper getAllTableMappingName() {
		logger.info("getAllTableMappingName method called...");
		return TableMappingService.getAllTableMappingName();
	}

	@GetMapping("/getTableMapping/{linkCode}")
	public ResultMapper getDataMapingByViewCode(@PathVariable String linkCode) {
		logger.info("getTableMapping by linkCode method called...");
		return TableMappingService.getTableMappingBydataMapCode(linkCode);
	}

	@PostMapping("/setTableMapping")
	public ResultMapper setDataMapingInfo(TableMappingDTO DataMapingDTO, Model model) {
		logger.info("setTableMapping method called..." + DataMapingDTO);
		return TableMappingService.setTableMapping(DataMapingDTO);
	}

	@PostMapping("/deleteTableMapping")
	public ResultMapper deleteTableMapping(Long id, Model model) {
		logger.info("deleteTableMapping method called..." + id);
		return TableMappingService.deleteTableMapping(id);
	}

}