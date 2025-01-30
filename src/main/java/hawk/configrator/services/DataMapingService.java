package hawk.configrator.services;

import java.util.List;

import hawk.configrator.dtos.DataMapingDTO;
import hawk.configrator.entities.DataMapingInfo;
import hawk.dtos.ResultMapper;

public interface DataMapingService {
	ResultMapper setDataMaping(DataMapingDTO viewDTO);

	ResultMapper getDataMaping();

	ResultMapper getDataMapingByid(Long id);

	DataMapingInfo getDataMapingInfoByid(Long id);

	DataMapingInfo getDataMapingInfoByCode(String code);

	ResultMapper deleteDataMaping(Long id);

	List<String> qTagList();

	ResultMapper getAllDataMapingName();

	ResultMapper getDataMapingByLinkCode(String linkCode);

	List<DataMapingDTO> getDataMapingByTargetViewId(Long targetViewId);

}
