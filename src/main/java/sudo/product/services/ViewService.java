package sudo.product.services;

import java.util.List;

import sudo.product.dtos.ViewDTO;
import sudo.product.entities.ViewInfo;
import sudo.user.dtos.ResultMapper;

public interface ViewService {
	ResultMapper getView();
	ResultMapper getViewByid(Long id);
	ViewInfo getViewInfoByid(Long id);
	ViewInfo getViewInfoByCode(String code);
	ViewDTO getAllQuestionsByViewid(Long id);
	List<String> qTagList();
	ResultMapper getAllViewName();

}
