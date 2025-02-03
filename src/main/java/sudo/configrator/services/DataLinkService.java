package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.DataLinkDTO;
import sudo.configrator.entities.DataLinkInfo;
import sudo.dtos.ResultMapper;

public interface DataLinkService {
	ResultMapper setDataLink(DataLinkDTO viewDTO);

	ResultMapper getDataLink();

	ResultMapper getDataLinkByid(Long id);

	DataLinkInfo getDataLinkInfoByid(Long id);

	DataLinkInfo getDataLinkInfoByCode(String code);

	ResultMapper deleteDataLink(Long id);

	List<String> qTagList();

	ResultMapper getAllDataLinkName();

	ResultMapper getDataLinkByLinkCode(String linkCode);

	List<DataLinkDTO> getDataLinkByTargetViewId(Long targetViewId);

}
