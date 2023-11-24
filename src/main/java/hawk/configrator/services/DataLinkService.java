package hawk.configrator.services;

import java.util.List;

import hawk.configrator.dtos.DataLinkDTO;
import hawk.configrator.entities.DataLinkInfo;
import hawk.dtos.ResultMapper;

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

}
