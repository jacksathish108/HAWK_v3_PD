package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.TableMappingDTO;
import sudo.configrator.entities.TableMappingInfo;
import sudo.dtos.ResultMapper;

public interface TableMappingService {
	ResultMapper setTableMapping(TableMappingDTO viewDTO);

	ResultMapper getTableMapping();

	ResultMapper getTableMappingByid(Long id);

	TableMappingInfo getTableMappingInfoByid(Long id);

	TableMappingInfo getTableMappingInfoByCode(String code);

	ResultMapper deleteTableMapping(Long id);

	List<String> qTagList();

	ResultMapper getAllTableMappingName();

	ResultMapper getTableMappingBydataMapCode(String dataMapCode);

	ResultMapper getTableMappingBySourcePageIdandViewId(Long sourcePageId,Long sourceViewId);

}
