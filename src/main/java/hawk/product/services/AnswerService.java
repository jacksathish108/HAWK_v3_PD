package hawk.product.services;

import java.util.List;
import java.util.Map;

import hawk.configrator.dtos.ListViewAnswerDTO;
import hawk.dtos.ResultMapper;

public interface AnswerService {
	ResultMapper setAnswer(Map answerMap);
	ResultMapper getAnswer();
	//ResultMapper getAnswerByid(Long id);
	ResultMapper getAnswersByViewId(Long pageId,Long viewId);
	ResultMapper deleteAnswer(Long id);
	ResultMapper getAnswersByQtag(String Qtag);
	List<ListViewAnswerDTO> getAnswersByListViewTargetQtags(List targetQtags);
	
}
