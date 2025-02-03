package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.ViewDTO;
import sudo.configrator.entities.ViewInfo;
import sudo.dtos.ResultMapper;

public interface ViewService {
	ResultMapper setView(ViewDTO viewDTO);

	ResultMapper getView();

	ResultMapper getViewByid(Long id);

	ViewInfo getViewInfoByid(Long id);

	ViewInfo getViewInfoByCode(String code);
	
	ViewDTO getAllQuestionsByViewid(Long id);

	ResultMapper deleteView(Long id);

	List<String> qTagList();

	ResultMapper getAllViewName();

}
