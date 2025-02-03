package sudo.product.services;

import java.util.List;

import sudo.product.dtos.DataLinkDTO;
import sudo.product.entities.DataLinkInfo;
import sudo.user.dtos.ResultMapper;

public interface DataLinkService {

	ResultMapper getDataLink();
	List<String> qTagList();
	ResultMapper getAllDataLinkName();
	ResultMapper getDataLinkByLinkCode(String linkCode);
	List<DataLinkDTO> getDataLinkByTargetViewId(Long targetViewId);

}
