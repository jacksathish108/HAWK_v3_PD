package sudo.product.services;

import java.util.List;

import sudo.product.dtos.DataMapingDTO;
import sudo.product.entities.DataMapingInfo;
import sudo.user.dtos.ResultMapper;

public interface DataMapingService {
	ResultMapper getDataMaping();
	ResultMapper getDataMapingByid(Long id);
	DataMapingInfo getDataMapingInfoByid(Long id);
	DataMapingInfo getDataMapingInfoByCode(String code);
	List<String> qTagList();
	ResultMapper getAllDataMapingName();
	ResultMapper getDataMapingByLinkCode(String linkCode);
	List<DataMapingDTO> getDataMapingByTargetViewId(Long targetViewId);

}
