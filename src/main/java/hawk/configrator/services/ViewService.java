package hawk.configrator.services;

import java.util.List;

import hawk.configrator.dtos.ViewDTO;
import hawk.configrator.entities.ViewInfo;
import hawk.dtos.ResultMapper;

public interface ViewService {
	ResultMapper setView(ViewDTO viewDTO);

	ResultMapper getView();

	ResultMapper getViewByid(Long id);

	ViewInfo getViewInfoByid(Long id);

	ViewInfo getViewInfoByCode(String code);

	ResultMapper deleteView(Long id);

	List<String> qTagList();

	ResultMapper getAllViewName();

}
