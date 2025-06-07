package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.DataMapingDTO;
import sudo.configrator.entities.DataMapingInfo;
import sudo.dtos.ResultMapper;

public interface DataMappingService {
	ResultMapper setDataMaping(DataMapingDTO viewDTO);

	ResultMapper getDataMaping();

	ResultMapper getDataMapingByid(Long id);

	DataMapingInfo getDataMapingInfoByid(Long id);

	DataMapingInfo getDataMapingInfoByCode(String code);

	ResultMapper deleteDataMaping(Long id);

	List<String> qTagList();

	ResultMapper getAllDataMapingName();

	ResultMapper getDataMapingBydataMapCode(String dataMapCode);

	ResultMapper getDataMapingBySourcePageIdandViewId(Long sourcePageId,Long sourceViewId);

}
